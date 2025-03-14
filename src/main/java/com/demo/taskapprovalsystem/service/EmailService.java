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

    // This method sends an email asynchronously
    // This method sends email notifications to all approvers asynchronously
    @Async
    public void sendApprovalNotifications(List<User> approvers, Task task) {
        for (User approver : approvers) {
            String subject = "Task Approval Required"; // Subject
            String message = "Please approve the task: " + task.getTaskName(); // Body of the email
            sendEmail(approver.getEmail(), subject, message);
        }
    }

    // Send email to task creator notifying them that an approver has approved the task
    @Async
    public void sendApprovalNotificationToCreator(Task task, User approver) {
        String subject = "Task Approved by " + approver.getName();
        String message = "The task '" + task.getTaskName() + "' has been approved by " + approver.getName() + ".";
        sendEmail(task.getCreator().getEmail(), subject, message);
    }

    // Send email to all approvers and task creator notifying them that all approvers have approved the task
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

    private void sendEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

}

