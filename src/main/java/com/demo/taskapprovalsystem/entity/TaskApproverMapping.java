package com.demo.taskapprovalsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_approver_mapping")
@Getter
@Setter
public class TaskApproverMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")  // Foreign key to the 'tasks' table
    private Task task;  // The task associated with this approver mapping

    @ManyToOne
    @JoinColumn(name = "approved_by", referencedColumnName = "id")  // Foreign key to the 'users' table (approver)
    private User approver;  // The user who needs to approve the task

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "status")
    private String status;


}
