package com.zerodefects.task.manager.service.mapper;


import com.zerodefects.task.manager.repository.entity.Task;
import com.zerodefects.task.manager.service.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskServiceMapper {

    Task serviceModelToEntity(TaskModel serviceModel);

    TaskModel entityToServiceModel(Task task);
}
