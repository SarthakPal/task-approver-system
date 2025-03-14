package com.demo.taskapprovalsystem.repository;

import com.demo.taskapprovalsystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // You can add custom query methods if needed
}

