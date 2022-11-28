package com.zerodefects.task.manager.rest.api.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAttachmentCreationRequest {

    private String taskId;
    private MultipartFile file;
}
