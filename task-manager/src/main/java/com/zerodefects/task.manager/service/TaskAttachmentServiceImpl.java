package com.zerodefects.task.manager.service;


import com.zerodefects.task.manager.repository.TaskAttachmentRepository;
import com.zerodefects.task.manager.repository.entity.TaskAttachment;
import com.zerodefects.task.manager.service.mapper.TaskAttachmentServiceMapper;
import com.zerodefects.task.manager.service.model.TaskAttachmentModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskAttachmentServiceImpl implements TaskAttachmentService {

    private final TaskAttachmentRepository repository;
    private final TaskAttachmentServiceMapper mapper;


    @Override
    public String create(TaskAttachmentModel serviceModel) {
        TaskAttachment entity = mapper.serviceModelToEntity(serviceModel);
        repository.save(entity);
        return entity.getId();
    }


    @Override
    public TaskAttachmentModel get(String id) {
        Optional<TaskAttachment> byId = repository.findById(id);
        //todo : throws exception
        return byId.map(mapper::entityToServiceModel).orElse(null);
    }

    @Override
    public void delete(String id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            // todo : throws exception
        }
    }


    @Override
    public List<TaskAttachmentModel> list(String id) {
        List<TaskAttachment> taskAttachmentModelList = repository.findByTaskId(id);
        taskAttachmentModelList.forEach(item -> item.setData(null));
        return taskAttachmentModelList.stream()
                .map(mapper::entityToServiceModel)
                .collect(Collectors.toList());
    }

}
