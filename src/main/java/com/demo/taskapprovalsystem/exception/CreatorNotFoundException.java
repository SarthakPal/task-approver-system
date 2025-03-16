package com.demo.taskapprovalsystem.exception;

/**
 * Custom exception to be thrown when a creator is not found.
 */
public class CreatorNotFoundException extends RuntimeException {

    /**
     * Constructor for CreatorNotFoundException.
     *
     * @param message The error message.
     */
    public CreatorNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for CreatorNotFoundException with message and cause.
     *
     * @param message The error message.
     * @param cause   The cause of the exception.
     */
    public CreatorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
