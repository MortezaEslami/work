package com.zerodefects.task.manager.rest.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskResponse extends TaskCreationRequest {
    private String id;
    private Date createdDate;
    private String createdBy;
}
