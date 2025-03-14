package com.demo.taskapprovalsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "status")
    private String status;  // e.g., Pending, Approved

    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "id")  // Foreign key to the 'User' table
    private User creator;  // The creator of the task

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // Define the One-to-Many relationship with TaskApproverMapping
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // To avoid circular references during JSON serialization
    private List<TaskApproverMapping> approverMappings;  // A task can have multiple approvers
}
