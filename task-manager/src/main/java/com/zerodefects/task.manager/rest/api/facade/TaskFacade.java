package com.zerodefects.task.manager.rest.api.facade;


import com.zerodefects.task.manager.exception.ResourceNotFoundException;
import com.zerodefects.task.manager.rest.api.dto.TaskCreationRequest;
import com.zerodefects.task.manager.rest.api.dto.TaskResponse;
import com.zerodefects.task.manager.service.TaskService;
import com.zerodefects.task.manager.service.model.TaskModel;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Component
@AllArgsConstructor
public class TaskFacade {
    private final TaskService service;
    private final TaskFacadeMapper mapper;
    private final MessageSource messageSource;
    private final WebRequest request;


    public TaskResponse create(TaskCreationRequest taskCreationRequest) {
        TaskModel serviceModel = mapper.creationRequestToServiceModel(taskCreationRequest);
        serviceModel = service.create(serviceModel);
        return mapper.serviceModelToResponse(serviceModel);
    }

    public List<TaskResponse> list() {
        return service.list().stream()
                .map(mapper::serviceModelToResponse)
                .toList();
    }

    public TaskResponse get(String id) {
        TaskModel task = service.get(id);
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

    public void update(TaskCreationRequest taskCreationRequest, String id) {
        TaskModel serviceModel = mapper.creationRequestToServiceModel(taskCreationRequest);
        serviceModel.setId(id);
        if (!service.update(serviceModel)) {
            throw new ResourceNotFoundException(messageSource.getMessage("resource.not.found.for.your.request",
                    null, request.getLocale()) + id);
        }
    }
}
