package com.zerodefects.task.manager.service;


import com.zerodefects.task.manager.repository.TaskRepository;
import com.zerodefects.task.manager.repository.entity.Task;
import com.zerodefects.task.manager.service.mapper.TaskServiceMapper;
import com.zerodefects.task.manager.service.model.TaskModel;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final TaskServiceMapper mapper;


    @Override
    public TaskModel create(TaskModel serviceModel) {
        Task entity = mapper.serviceModelToEntity(serviceModel);
        entity.setCreatedDate(new Date());
        String username = ((SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication().getDetails())
                .getKeycloakSecurityContext().getToken().getPreferredUsername();
        entity.setCreatedBy(username);
        entity = repository.save(entity);
        return mapper.entityToServiceModel(entity);
    }

    @Transactional
    @Override
    public boolean update(TaskModel serviceModel) {
        Optional<Task> byId = repository.findById(serviceModel.getId());
        byId.ifPresent(item -> {
            item.setId(serviceModel.getId());
            item.setTitle(serviceModel.getTitle());
            item.setDescription(serviceModel.getDescription());
            item.setTaskTime(serviceModel.getTaskTime());
            repository.save(item);
        });
        return byId.isPresent();
    }

    @Override
    public List<TaskModel> list() {
        return StreamSupport.stream(repository.findAllByOrderByCreatedDateDesc().spliterator(), false)
                .map(mapper::entityToServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public TaskModel get(String id) {
        Optional<Task> byId = repository.findById(id);
        return byId.map(mapper::entityToServiceModel).orElse(null);
    }

    @Override
    public boolean delete(String id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
