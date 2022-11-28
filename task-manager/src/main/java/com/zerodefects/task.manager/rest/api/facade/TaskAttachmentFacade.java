package com.zerodefects.task.manager.rest.api.facade;


import com.zerodefects.task.manager.rest.api.dto.TaskAttachmentCreationRequest;
import com.zerodefects.task.manager.rest.api.dto.TaskAttachmentResponse;
import com.zerodefects.task.manager.service.TaskAttachmentService;
import com.zerodefects.task.manager.service.model.TaskAttachmentModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TaskAttachmentFacade {
    private final TaskAttachmentService service;
    private final TaskAttachmentFacadeMapper mapper;


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
        return mapper.serviceModelToResponse(task);
    }

    public void delete(String id) {
        service.delete(id);
    }

    public List<TaskAttachmentResponse> list(String id) {
        return service.list(id).stream()
                .map(mapper::serviceModelToResponse)
                .collect(Collectors.toList());
    }
}
