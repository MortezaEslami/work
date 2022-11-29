package com.zerodefects.taskmanager.service.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class TaskModel {
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    private Integer taskTime;
    private Date createdDate;
    private String createdBy;
}
