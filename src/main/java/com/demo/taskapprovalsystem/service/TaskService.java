package com.demo.taskapprovalsystem.service;

import com.demo.taskapprovalsystem.constants.ApplicationConstants;
import com.demo.taskapprovalsystem.entity.Task;
import com.demo.taskapprovalsystem.entity.TaskApproverMapping;
import com.demo.taskapprovalsystem.entity.User;
import com.demo.taskapprovalsystem.exception.*;
import com.demo.taskapprovalsystem.repository.TaskApproverMappingRepository;
import com.demo.taskapprovalsystem.repository.TaskRepository;
import com.demo.taskapprovalsystem.repository.UserRepository;
import com.demo.taskapprovalsystem.request.CreateTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskApproverMappingRepository taskApproverMappingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    /**
     * Creates a new task and assigns approvers to it.
     *
     * This method receives a **CreateTaskRequest** object containing the task details, such as task name, creator, and approvers.
     * It first fetches the **creator** from the user repository based on the provided **createdBy** field. Then, it creates a
     * new task, sets its properties, and saves it to the database. After that, it assigns **approvers** to the task, creates
     * task-approver mappings, and saves them. Finally, it sends an asynchronous email notification to the approvers about the new task.
     *
     * @param createTaskRequest The request object containing task details, including the task name, creator ID, and list of approvers.
     * @return The created **Task** object, saved and with assigned approvers.
     * @throws CreatorNotFoundException If the creator is not found in the system or if an unexpected error occurs during task creation.
     */
    @Transactional
    public Task createTask(CreateTaskRequest createTaskRequest) {

        User creator = userRepository.findById(createTaskRequest.getCreatedBy())
                .orElseThrow(() -> new CreatorNotFoundException("Creator not found with ID: " + createTaskRequest.getCreatedBy()));

        Task task = new Task();
        task.setTaskName(createTaskRequest.getTaskName());
        task.setCreator(creator);
        task.setCreatedDate(LocalDateTime.now());
        task.setStatus(ApplicationConstants.CREATED);

        Task savedTask = taskRepository.save(task);

        List<User> approvers = userRepository.findAllById(createTaskRequest.getApprovedBy());

        for (User approver : approvers) {
            TaskApproverMapping mapping = new TaskApproverMapping();
            mapping.setTask(savedTask);
            mapping.setApprover(approver);
            mapping.setStatus(ApplicationConstants.PENDING);
            mapping.setCreatedAt(LocalDateTime.now());
            taskApproverMappingRepository.save(mapping);
        }

        emailService.sendApprovalNotifications(approvers, savedTask);

        return savedTask;
    }

    /**
     * Approves a task by an approver and updates its status.
     *
     * This method allows an approver to approve a task. It first checks if the **task** and **approver** exist. Then,
     * it verifies if the approver is authorized to approve the task. Upon successful approval, it updates the
     * **`TaskApproverMapping`** status to "APPROVED" and records the approval timestamp. If all approvers have approved the task,
     * the task's status is updated to "ALL_APPROVED" and an email notification is sent to the task creator and all approvers.
     * Otherwise, the task status is updated to "PENDING" or "PARTIALLY APPROVED".
     *
     * @param taskId The ID of the task to be approved.
     * @param approverId The ID of the user who is approving the task.
     * @return The updated **Task** object with the updated status.
     * @throws RuntimeException If the task or approver is not found, or if the approver is not authorized to approve the task.
     */
    @Transactional
    public Task approveTask(Long taskId, Long approverId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(ApplicationConstants.TASK_NOT_FOUND));
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new UserNotFoundException(ApplicationConstants.APPROVER_NOT_FOUND));

        TaskApproverMapping mapping = taskApproverMappingRepository.findByTaskAndApprover(task, approver)
                .orElseThrow(() -> new ApproverNotAuthorizedException(ApplicationConstants.APPROVER_NOT_AUTHORIZED));

        mapping.setStatus(ApplicationConstants.APPROVED);
        mapping.setApprovedAt(LocalDateTime.now());
        taskApproverMappingRepository.save(mapping);

        emailService.sendApprovalNotificationToCreator(task, approver);

        if (allApproversApproved(task)) {

            task.setStatus(ApplicationConstants.ALL_APPROVED);
            taskRepository.save(task);

            emailService.sendAllApproversApprovalNotification(task);
        } else {
            task.setStatus(ApplicationConstants.PENDING);
            taskRepository.save(task);
        }

        return task;
    }


    /**
     * Checks if all approvers have approved the task.
     *
     * This method verifies if every approver associated with the given task has approved it.
     * It queries the **`TaskApproverMapping`** entries for the task and counts the number of approvers whose status is "APPROVED".
     * If the count of approvers who have approved the task matches the total number of approvers, it returns **true**.
     * Otherwise, it returns **false** indicating that not all approvers have approved the task.
     *
     * @param task The task object for which the approval status of all approvers needs to be checked.
     * @return **true** if all approvers have approved the task; **false** otherwise.
     */
    private boolean allApproversApproved(Task task) {
        List<TaskApproverMapping> mappings = taskApproverMappingRepository.findByTask(task);
        long approvedCount = mappings.stream()
                .filter(mapping -> mapping.getStatus().equals(ApplicationConstants.APPROVED))
                .count();
        return approvedCount == mappings.size();
    }

}

