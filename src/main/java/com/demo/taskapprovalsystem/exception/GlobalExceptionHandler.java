package com.demo.taskapprovalsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Global exception handler for handling user-related exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserRegistrationException and returns a custom error response.
     *
     * @param ex the thrown exception
     * @return a ResponseEntity with the error message
     */
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handleUserRegistrationException(UserRegistrationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
