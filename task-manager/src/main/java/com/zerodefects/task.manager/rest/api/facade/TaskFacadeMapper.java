package com.zerodefects.task.manager.rest.api.facade;


import com.zerodefects.task.manager.rest.api.dto.TaskCreationRequest;
import com.zerodefects.task.manager.rest.api.dto.TaskResponse;
import com.zerodefects.task.manager.service.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskFacadeMapper {

    TaskModel creationRequestToServiceModel(TaskCreationRequest taskCreationRequest);

    TaskResponse serviceModelToResponse(TaskModel taskModel);

}
