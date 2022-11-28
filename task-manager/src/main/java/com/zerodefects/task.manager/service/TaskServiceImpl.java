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
    public void update(TaskModel serviceModel) {
        Optional.of(repository
                        .findById(serviceModel.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(directory -> {
                    directory.setId(serviceModel.getId());
                    directory.setTitle(serviceModel.getTitle());
                    directory.setDescription(serviceModel.getDescription());
                    directory.setTaskTime(serviceModel.getTaskTime());
                    repository.save(directory);
                    return directory;
                });
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
}
