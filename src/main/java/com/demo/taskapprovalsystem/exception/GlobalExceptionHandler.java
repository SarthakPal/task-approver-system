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

    /**
     * Handles CreatorNotFoundException and returns a custom error response.
     *
     * @param ex The exception thrown.
     * @return A ResponseEntity containing the error message with 404 NOT FOUND status.
     */
    @ExceptionHandler(CreatorNotFoundException.class)
    public ResponseEntity<String> handleCreatorNotFoundException(CreatorNotFoundException ex) {
        // Return a 404 NOT FOUND response with the exception message
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    /**
     * Handles TaskNotFoundException and returns a custom error response.
     *
     * @param ex The exception thrown.
     * @return A ResponseEntity containing the error message with 404 NOT FOUND status.
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException ex) {
        // Return a 404 NOT FOUND response with the exception message
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    /**
     * Handles ApproverNotAuthorizedException and returns a custom error response.
     *
     * @param ex The exception thrown.
     * @return A ResponseEntity containing the error message with 404 NOT FOUND status.
     */
    @ExceptionHandler(ApproverNotAuthorizedException.class)
    public ResponseEntity<String> handleApproverNotAuthorizedException(ApproverNotAuthorizedException ex) {
        // Return a 404 NOT FOUND response with the exception message
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
