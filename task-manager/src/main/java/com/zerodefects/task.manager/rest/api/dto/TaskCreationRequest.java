package com.zerodefects.task.manager.rest.api.dto;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class TaskCreationRequest {

    @NonNull
    private String title;

    @NonNull
    private String description;
    private Integer taskTime;

}
