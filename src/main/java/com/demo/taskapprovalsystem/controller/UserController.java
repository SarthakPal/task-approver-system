package com.demo.taskapprovalsystem.controller;

import com.demo.taskapprovalsystem.entity.User;
import com.demo.taskapprovalsystem.exception.UserRegistrationException;
import com.demo.taskapprovalsystem.repository.UserRepository;
import com.demo.taskapprovalsystem.request.CreateUserRequest;
import com.demo.taskapprovalsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Handles the user signup process by registering a new user.
     *
     * This method receives a **CreateUserRequest** containing the necessary data for user registration, such as the user's details
     * (name, email, password, etc.). The method attempts to register the user and generate a unique login ID. If successful,
     * it returns the newly created user along with the generated login ID. If an error occurs (such as the email already being taken),
     * it returns a **400 Bad Request** response.
     *
     * @param creatUserRequest The request body containing the user information for registration. This includes user data such as
     *                         name, email, and password.
     * @return A **ResponseEntity** containing the newly created **User** object with the generated login ID.
     *         If registration fails, it returns a **400 Bad Request** response with a **null** body.
     */
    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest creatUserRequest) {
        try {
            User newUser = userService.registerUser(creatUserRequest);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            throw new UserRegistrationException("Error registering user: " + e.getMessage());
        }
    }

}

