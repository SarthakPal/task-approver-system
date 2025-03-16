package com.demo.taskapprovalsystem.service;

import com.demo.taskapprovalsystem.constants.ApplicationConstants;
import com.demo.taskapprovalsystem.entity.Comment;
import com.demo.taskapprovalsystem.entity.Task;
import com.demo.taskapprovalsystem.entity.User;
import com.demo.taskapprovalsystem.exception.TaskNotFoundException;
import com.demo.taskapprovalsystem.exception.UserNotFoundException;
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

    /**
     * Adds a comment to a task.
     *
     * This method retrieves the **Task** and **User** from the database using the provided **taskId** and **userId**.
     * It then creates a new **Comment** object, associates it with the task and user, and saves it to the database.
     * The comment's creation timestamp is also set to the current time.
     *
     * @param taskId The ID of the task to which the comment is being added.
     * @param userId The ID of the user who is adding the comment.
     * @param commentText The text content of the comment.
     * @return The **Comment** object that was added to the task, saved in the database.
     * @throws TaskNotFoundException If no task is found with the provided **taskId**.
     * @throws UserNotFoundException If no user is found with the provided **userId**.
     */
    @Transactional
    public Comment addComment(Long taskId, Long userId, String commentText) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(ApplicationConstants.TASK_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ApplicationConstants.USER_NOT_FOUND));

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setUser(user);
        comment.setCommentText(commentText);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

}
