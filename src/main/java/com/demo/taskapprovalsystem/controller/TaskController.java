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

    // Endpoint to create a task
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        // Call the service to create a task
        Task createdTask = taskService.createTask(createTaskRequest);

        // Return the created task with 200 OK response
        return ResponseEntity.ok(createdTask);
    }

    // API endpoint for an approver to approve a task
    @PostMapping("/{taskId}/approve")
    public ResponseEntity<Task> approveTask(@PathVariable Long taskId, @RequestParam Long approverId) {
        // Call the service to approve the task
        Task approvedTask = taskService.approveTask(taskId, approverId);

        // Return the updated task with 200 OK response
        return ResponseEntity.ok(approvedTask);
    }
}

