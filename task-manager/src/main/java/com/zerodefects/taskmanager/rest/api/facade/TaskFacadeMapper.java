package com.zerodefects.taskmanager.rest.api.facade;


import com.zerodefects.taskmanager.rest.api.dto.TaskCreationRequest;
import com.zerodefects.taskmanager.rest.api.dto.TaskResponse;
import com.zerodefects.taskmanager.service.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskFacadeMapper {

    TaskModel creationRequestToServiceModel(TaskCreationRequest taskCreationRequest);

    TaskResponse serviceModelToResponse(TaskModel taskModel);

}
