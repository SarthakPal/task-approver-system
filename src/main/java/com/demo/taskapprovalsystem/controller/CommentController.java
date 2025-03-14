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

    // API endpoint for adding a comment to a task
    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestParam Long taskId,
                                              @RequestParam Long userId,
                                              @RequestParam String commentText) {
        // Call the service to add the comment
        Comment comment = commentService.addComment(taskId, userId, commentText);

        // Return the created comment with a 200 OK response
        return ResponseEntity.ok(comment);
    }
}
