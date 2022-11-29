package com.zerodefects.taskmanager.service.mapper;


import com.zerodefects.taskmanager.repository.entity.Task;
import com.zerodefects.taskmanager.service.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskServiceMapper {

    Task serviceModelToEntity(TaskModel serviceModel);

    TaskModel entityToServiceModel(Task task);
}
