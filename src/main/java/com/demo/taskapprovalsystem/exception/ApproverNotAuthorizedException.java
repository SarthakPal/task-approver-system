package com.demo.taskapprovalsystem.exception;

/**
 * Custom exception to be thrown when a approver is not authorized to apporve a task.
 */
public class ApproverNotAuthorizedException extends RuntimeException {

    /**
     * Constructor for ApproverNotAuthorizedException.
     *
     * @param message The error message.
     */
    public ApproverNotAuthorizedException(String message) {
        super(message);
    }

    /**
     * Constructor for ApproverNotAuthorizedException with message and cause.
     *
     * @param message The error message.
     * @param cause   The cause of the exception.
     */
    public ApproverNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
