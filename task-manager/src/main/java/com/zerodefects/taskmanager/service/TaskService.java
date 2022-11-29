package com.zerodefects.taskmanager.service;

import com.zerodefects.taskmanager.service.model.TaskModel;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface TaskService {
    TaskModel create(@Validated TaskModel serviceModel);

    boolean update(@Validated TaskModel serviceModel);

    List<TaskModel> list();

    TaskModel get(String id);

    boolean delete(String id);
}
