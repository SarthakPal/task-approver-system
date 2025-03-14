package com.demo.taskapprovalsystem.request;

import com.demo.taskapprovalsystem.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTaskRequest {

    private String taskName;
    private Long createdBy;
    private List<Long> approvedBy;


}
