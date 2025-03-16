package com.demo.taskapprovalsystem.exception;

/**
 * Custom exception to be thrown when a task is not found.
 */
public class TaskNotFoundException extends RuntimeException {

    /**
     * Constructor for TaskNotFoundException.
     *
     * @param message The error message.
     */
    public TaskNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for TaskNotFoundException with message and cause.
     *
     * @param message The error message.
     * @param cause   The cause of the exception.
     */
    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
