package com.demo.taskapprovalsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;  // The task associated with the comment

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;  // The user who created the comment

    @Column(name = "comment_text")
    private String commentText;  // The text of the comment

    @Column(name = "created_at")
    private LocalDateTime createdAt;  // Timestamp when the comment was created

    // Constructor
    public Comment() {}

    public Comment(Task task, User user, String commentText, LocalDateTime createdAt) {
        this.task = task;
        this.user = user;
        this.commentText = commentText;
        this.createdAt = createdAt;
    }
}
