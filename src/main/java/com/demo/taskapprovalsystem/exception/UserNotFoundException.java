package com.demo.taskapprovalsystem.exception;

/**
 * Custom exception class to handle user not found errors.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
