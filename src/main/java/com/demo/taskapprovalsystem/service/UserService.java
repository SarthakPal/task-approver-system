package com.demo.taskapprovalsystem.service;

import com.demo.taskapprovalsystem.entity.User;
import com.demo.taskapprovalsystem.exception.UserRegistrationException;
import com.demo.taskapprovalsystem.mapper.UserRegistrationMapper;
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
    private PasswordEncoder passwordEncoder;


    /**
     * Registers a new user.
     *
     * @param createUserRequest The user details from the client to be saved.
     * @return The registered User entity.
     */
    public User registerUser(CreateUserRequest createUserRequest) {

        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new UserRegistrationException("User with this email already exists");
        }

        String loginId = generateLoginId(createUserRequest.getEmail());
        String hashedPassword = passwordEncoder.encode(createUserRequest.getPassword());

        UserRegistrationMapper.INSTANCE.mapToUserWithDynamicFields(createUserRequest, loginId, hashedPassword);

        User user = new User(createUserRequest.getName(), createUserRequest.getEmail(), hashedPassword, loginId);
        return userRepository.save(user);
    }

    /**
     * Generates a login ID based on the email (simple example)
     *
     * @param email The user's email.
     * @return The generated login ID.
     */
    private String generateLoginId(String email) {
        // Here, we are using the part before the "@" of the email as the login ID.
        return email.split("@")[0];
    }
}

