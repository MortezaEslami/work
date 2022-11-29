package com.zerodefects.taskmanager.rest.api.facade;


import com.zerodefects.taskmanager.exception.ResourceNotFoundException;
import com.zerodefects.taskmanager.rest.api.dto.TaskAttachmentCreationRequest;
import com.zerodefects.taskmanager.rest.api.dto.TaskAttachmentResponse;
import com.zerodefects.taskmanager.service.TaskAttachmentService;
import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class TaskAttachmentFacade {
    private final TaskAttachmentService service;
    private final TaskAttachmentFacadeMapper mapper;
    private final MessageSource messageSource;
    private final WebRequest request;


    public String create(TaskAttachmentCreationRequest taskAttachmentCreationRequest) {
        TaskAttachmentModel serviceModel = new TaskAttachmentModel();
        serviceModel.setTaskId(taskAttachmentCreationRequest.getTaskId());
        serviceModel.setName(taskAttachmentCreationRequest.getFile().getOriginalFilename());
        serviceModel.setType(taskAttachmentCreationRequest.getFile().getContentType());
        try {
            serviceModel.setData(taskAttachmentCreationRequest.getFile().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return service.create(serviceModel);
    }

    public TaskAttachmentResponse get(String id) {
        TaskAttachmentModel task = service.get(id);
        if (task != null) {
            return mapper.serviceModelToResponse(task);
        } else {
            throw new ResourceNotFoundException(messageSource.getMessage("resource.not.found.for.your.request",
                    null, request.getLocale()) + id);
        }
    }

    public void delete(String id) {
        if (!service.delete(id)) {
            throw new ResourceNotFoundException(messageSource.getMessage("resource.not.found.for.your.request",
                    null, request.getLocale()) + id);
        }
    }

    public List<TaskAttachmentResponse> list(String id) {
        return service.list(id).stream()
                .map(mapper::serviceModelToResponse)
                .toList();
    }
}
