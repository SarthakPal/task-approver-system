package com.demo.taskapprovalsystem.controller;

import com.demo.taskapprovalsystem.entity.Comment;
import com.demo.taskapprovalsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Adds a comment to a task.
     *
     * This method receives the **taskId**, **userId**, and **commentText** as request parameters and
     * calls the service to create a new **Comment** for the specified task. After the comment is successfully added,
     * it returns the created **Comment** object along with a **200 OK** HTTP response.
     *
     * @param taskId The ID of the task to which the comment is being added.
     * @param userId The ID of the user who is adding the comment.
     * @param commentText The text content of the comment.
     * @return A **ResponseEntity** containing the newly created **Comment** object with a **200 OK** status.
     * @throws RuntimeException If the task or user is not found, or if an error occurs during the comment creation.
     */
    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestParam Long taskId,
                                              @RequestParam Long userId,
                                              @RequestParam String commentText) {

        Comment comment = commentService.addComment(taskId, userId, commentText);
        return ResponseEntity.ok(comment);
    }

}
