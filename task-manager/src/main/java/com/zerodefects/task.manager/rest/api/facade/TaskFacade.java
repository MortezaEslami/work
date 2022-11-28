package com.zerodefects.task.manager.rest.api.facade;


import com.zerodefects.task.manager.rest.api.dto.TaskCreationRequest;
import com.zerodefects.task.manager.rest.api.dto.TaskResponse;
import com.zerodefects.task.manager.service.TaskService;
import com.zerodefects.task.manager.service.model.TaskModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TaskFacade {
    private final TaskService service;
    private final TaskFacadeMapper mapper;

    public TaskResponse create(TaskCreationRequest taskCreationRequest) {
        TaskModel serviceModel = mapper.creationRequestToServiceModel(taskCreationRequest);
        serviceModel = service.create(serviceModel);
        return mapper.serviceModelToResponse(serviceModel);
    }

    public List<TaskResponse> list() {

        return service.list().stream()
                .map(mapper::serviceModelToResponse)
                .collect(Collectors.toList());

    }

    public TaskResponse get(String id) {

        TaskModel task = service.get(id);
        return mapper.serviceModelToResponse(task);
    }

    public void delete(String id) {
        service.delete(id);
    }

    public void update(TaskCreationRequest taskCreationRequest, String id) {
        TaskModel serviceModel = mapper.creationRequestToServiceModel(taskCreationRequest);
        serviceModel.setId(id);
        service.update(serviceModel);
    }
}
