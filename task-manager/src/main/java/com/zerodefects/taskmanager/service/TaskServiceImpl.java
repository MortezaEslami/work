package com.zerodefects.taskmanager.service;


import com.zerodefects.taskmanager.repository.TaskRepository;
import com.zerodefects.taskmanager.repository.entity.Task;
import com.zerodefects.taskmanager.service.mapper.TaskServiceMapper;
import com.zerodefects.taskmanager.service.model.TaskModel;
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
        String username = getCurrentUserUsername();
        entity.setCreatedBy(username);
        entity = repository.save(entity);
        return mapper.entityToServiceModel(entity);
    }

    public String getCurrentUserUsername() {
        return ((SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication().getDetails())
                .getKeycloakSecurityContext().getToken().getPreferredUsername();
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
