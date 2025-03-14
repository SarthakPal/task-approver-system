package com.demo.taskapprovalsystem.repository;

import com.demo.taskapprovalsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find a user by their email (email is used as username in your case)
    Optional<User> findByLoginId(String loginId);

    Optional<User> findByEmail(String email);
}

