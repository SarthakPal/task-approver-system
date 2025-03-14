package com.demo.taskapprovalsystem.controller;

import com.demo.taskapprovalsystem.entity.User;
import com.demo.taskapprovalsystem.repository.UserRepository;
import com.demo.taskapprovalsystem.request.CreateUserRequest;
import com.demo.taskapprovalsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject the password encoder

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest creatUserRequest) {
        try {
            // Register the user and generate the login ID
            User newUser = userService.registerUser(creatUserRequest);
            return ResponseEntity.ok(newUser); // Return the new user with login ID
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Handle errors like email already taken
        }
    }
}

