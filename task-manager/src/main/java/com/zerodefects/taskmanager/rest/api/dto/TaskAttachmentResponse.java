package com.zerodefects.taskmanager.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskAttachmentResponse extends TaskAttachmentCreationRequest {
    private String id;
    private String name;
    private String type;
    private byte[] data;
}
