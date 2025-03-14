package com.demo.taskapprovalsystem.service;

import com.demo.taskapprovalsystem.entity.User;
import com.demo.taskapprovalsystem.repository.UserRepository;
import com.demo.taskapprovalsystem.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // To hash passwords

    // Method to create a new user
    public User registerUser(CreateUserRequest createUserRequest) {
        // Check if the user with the given email already exists
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        // Generate an internal login ID (for example, use the email prefix)
        String loginId = generateLoginId(createUserRequest.getEmail());

        // Hash the password
        String hashedPassword = passwordEncoder.encode(createUserRequest.getPassword());

        // Create and save the user
        User user = new User(createUserRequest.getName(), createUserRequest.getEmail(), hashedPassword, loginId);
        return userRepository.save(user);
    }

    // Helper method to generate an internal login ID
    private String generateLoginId(String email) {
        // Here, we are using the part before the "@" of the email as the login ID.
        // You can customize this logic to suit your needs.
        return email.split("@")[0];
    }
}

