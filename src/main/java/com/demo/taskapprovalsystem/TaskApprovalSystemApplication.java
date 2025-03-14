package com.demo.taskapprovalsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  // Enables the use of @Async in the application
public class TaskApprovalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApprovalSystemApplication.class, args);
	}

}
