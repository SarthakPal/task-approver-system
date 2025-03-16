package com.demo.taskapprovalsystem.service;

import com.demo.taskapprovalsystem.entity.Task;
import com.demo.taskapprovalsystem.entity.TaskApproverMapping;
import com.demo.taskapprovalsystem.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends email notifications to all approvers asynchronously to inform them of the task approval.
     *
     * This method sends an email to each approver in the provided list, notifying them about a task that requires their approval.
     * The email contains the task's name and a request to approve it. The method executes asynchronously to ensure that the
     * email notifications do not block the main thread.
     *
     * @param approvers A list of **User** objects representing the approvers who need to receive the email notifications.
     * @param task The **Task** object that requires approval. The task's name is included in the email.
     */
    @Async
    public void sendApprovalNotifications(List<User> approvers, Task task) {
        for (User approver : approvers) {
            String subject = "Task Approval Required"; // Subject
            String message = "Please approve the task: " + task.getTaskName(); // Body of the email
            sendEmail(approver.getEmail(), subject, message);
        }
    }

    /**
     * Sends an email notification to the task creator informing them that an approver has approved the task.
     *
     * This method sends an email to the creator of the task, notifying them that the task has been approved by a specific approver.
     * The email includes the name of the approver and the task name.
     * The method runs asynchronously to ensure that sending the email does not block the main thread.
     *
     * @param task The **Task** object that has been approved. The task's name and creator's email are used in the email.
     * @param approver The **User** object representing the approver who has approved the task.
     */
    @Async
    public void sendApprovalNotificationToCreator(Task task, User approver) {
        String subject = "Task Approved by " + approver.getName();
        String message = "The task '" + task.getTaskName() + "' has been approved by " + approver.getName() + ".";
        sendEmail(task.getCreator().getEmail(), subject, message);
    }


    /**
     * Sends email notifications to all approvers and the task creator, informing them that the task has been fully approved by all approvers.
     *
     * This method sends an email to both the **task creator** and all **approvers**, notifying them that the task has been fully approved
     * by all the assigned approvers. The email subject indicates that the task has been fully approved, and the message contains the
     * task's name and a confirmation of full approval.
     *
     * The method executes asynchronously, meaning the email sending process will not block the main thread, allowing the system
     * to continue functioning without delays.
     *
     * @param task The **Task** object that has been fully approved. The task's name and approvers are used in the email.
     */
    @Async
    public void sendAllApproversApprovalNotification(Task task) {
        String subject = "Task Fully Approved";
        String message = "The task '" + task.getTaskName() + "' has been fully approved by all approvers.";

        // Send email to task creator
        sendEmail(task.getCreator().getEmail(), subject, message);

        // Send email to all approvers
        for (TaskApproverMapping mapping : task.getApproverMappings()) {
            sendEmail(mapping.getApprover().getEmail(), subject, message);
        }
    }

    /**
     * Sends an email to the specified recipient with the given subject and message.
     *
     * This method creates a **SimpleMailMessage** object and sets the recipient's email address,
     * subject, and message body. It then uses the **mailSender** to send the email.
     *
     * @param to The email address of the recipient.
     * @param subject The subject of the email.
     * @param message The body content of the email.
     */
    private void sendEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

}

