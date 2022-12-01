package com.zerodefects.taskmanager.rest.api.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TaskCreationRequest {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;
    private Integer taskTime;

}
