package com.demo.taskapprovalsystem.service;

import com.demo.taskapprovalsystem.entity.Comment;
import com.demo.taskapprovalsystem.entity.Task;
import com.demo.taskapprovalsystem.entity.User;
import com.demo.taskapprovalsystem.repository.CommentRepository;
import com.demo.taskapprovalsystem.repository.TaskRepository;
import com.demo.taskapprovalsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Comment addComment(Long taskId, Long userId, String commentText) {
        // Step 1: Fetch the Task and User by their IDs
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step 2: Create a new Comment object
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setUser(user);
        comment.setCommentText(commentText);
        comment.setCreatedAt(LocalDateTime.now());

        // Step 3: Save the comment to the database
        return commentRepository.save(comment);
    }
}
