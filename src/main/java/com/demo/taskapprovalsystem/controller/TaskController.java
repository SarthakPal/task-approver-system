package com.demo.taskapprovalsystem.controller;

import com.demo.taskapprovalsystem.entity.Task;
import com.demo.taskapprovalsystem.request.CreateTaskRequest;
import com.demo.taskapprovalsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Creates a new task based on the provided task details.
     *
     * This method receives a **`CreateTaskRequest`** object containing the necessary data (task name, creator,
     * approvers, etc.) for creating a task. The method passes this data to the service layer to process and create
     * the task. After the task is successfully created, it returns the created task along with a **200 OK** HTTP response.
     *
     * @param createTaskRequest The request body containing the task details such as the task name, creator, and
     *                          a list of approvers.
     * @return A **ResponseEntity** containing the newly created **Task** object, returned with an HTTP status of **200 OK**.
     *         If there is an issue during task creation, an appropriate error response would be returned.
     */
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        Task createdTask = taskService.createTask(createTaskRequest);
        return ResponseEntity.ok(createdTask);
    }


    /**
     * Approves a task by an approver.
     *
     * This method allows an approver to approve a task by providing the **taskId** and **approverId** as input.
     * The task's approval status is updated, and if all approvers have approved the task, the task's status is
     * updated to "ALL_APPROVED". An email notification is also sent to the task creator and the approvers.
     *
     * @param taskId The ID of the task to be approved.
     * @param approverId The ID of the user who is approving the task.
     * @return A **ResponseEntity** containing the updated **Task** object with the new approval status.
     * @throws RuntimeException If the task or approver is not found, or if the approver is not authorized to approve the task.
     */
    @PostMapping("/{taskId}/approve")
    public ResponseEntity<Task> approveTask(@PathVariable Long taskId, @RequestParam Long approverId) {
        Task approvedTask = taskService.approveTask(taskId, approverId);
        return ResponseEntity.ok(approvedTask);
    }

}

