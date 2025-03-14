package com.demo.taskapprovalsystem.service;

import com.demo.taskapprovalsystem.entity.Task;
import com.demo.taskapprovalsystem.entity.TaskApproverMapping;
import com.demo.taskapprovalsystem.entity.User;
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
    private TaskRepository taskRepository;  // Task repository to save task
    @Autowired
    private TaskApproverMappingRepository taskApproverMappingRepository;  // To save task-approver mappings
    @Autowired
    private UserRepository userRepository;  // To fetch user details based on user IDs
    @Autowired
    private EmailService emailService;  // To send email notifications

    @Transactional
    public Task createTask(CreateTaskRequest createTaskRequest) {
        // Step 1: Fetch the creator (User entity) using the 'createdBy' field
        User creator = userRepository.findById(createTaskRequest.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        // Step 2: Create a new Task
        Task task = new Task();
        task.setTaskName(createTaskRequest.getTaskName());
        task.setCreator(creator);
        task.setCreatedDate(LocalDateTime.now());
        task.setStatus("CREATED");  // Initially, the task is "Pending"

        // Save the task
        Task savedTask = taskRepository.save(task);

        // Step 3: Fetch the approvers (User entities) based on 'approvedBy' field
        List<User> approvers = userRepository.findAllById(createTaskRequest.getApprovedBy());

        // Step 4: Create TaskApproverMapping entries
        for (User approver : approvers) {
            TaskApproverMapping mapping = new TaskApproverMapping();
            mapping.setTask(savedTask);
            mapping.setApprover(approver);
            mapping.setStatus("PENDING");
            mapping.setCreatedAt(LocalDateTime.now());
            taskApproverMappingRepository.save(mapping);
        }

        // Step 5: Send email notification to the approver asynchronously
        emailService.sendApprovalNotifications(approvers, savedTask);


        // Step 6: Return the created task
        return savedTask;
    }


    @Transactional
    public Task approveTask(Long taskId, Long approverId) {
        // Step 1: Fetch the task and the approver
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        // Step 2: Check if the approver is in the list of approvers for the task
        TaskApproverMapping mapping = taskApproverMappingRepository.findByTaskAndApprover(task, approver)
                .orElseThrow(() -> new RuntimeException("Approver is not authorized to approve this task"));

        // Step 3: Set the approval date and mark the task as approved by this approver
        mapping.setStatus("APPROVED");
        mapping.setApprovedAt(LocalDateTime.now());
        taskApproverMappingRepository.save(mapping);

        // Send email to the task creator notifying them of the approval
        emailService.sendApprovalNotificationToCreator(task, approver);

        // Step 4: Check if all approvers have approved the task
        if (allApproversApproved(task)) {
            // If all approvers have approved, update task status to 'All Approved'
            task.setStatus("ALL_APPROVED");
            taskRepository.save(task);

            // Notify all users (task creator and approvers) that the task is fully approved
            emailService.sendAllApproversApprovalNotification(task);
        } else {
            // If not all approvers have approved, update task status to 'Pending' or 'Partially Approved'
            task.setStatus("PENDING");
            taskRepository.save(task);
        }

        // Step 5: Return the updated task
        return task;
    }

    private boolean allApproversApproved(Task task) {
        // Check if all approvers have approved the task
        List<TaskApproverMapping> mappings = taskApproverMappingRepository.findByTask(task);
        long approvedCount = mappings.stream()
                .filter(mapping -> mapping.getApprovedAt() != null)  // Count approvers who have approved
                .count();
        return approvedCount == mappings.size();  // If the number of approved matches total approvers
    }


}

