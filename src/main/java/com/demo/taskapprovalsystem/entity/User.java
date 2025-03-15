package com.demo.taskapprovalsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "login_id")
    private String loginId;// Add the password field
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public User(String name, String email, String hashedPassword, String loginId) {
    }
}



