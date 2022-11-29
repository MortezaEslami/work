package com.zerodefects.taskmanager.rest.api.facade;


import com.zerodefects.taskmanager.rest.api.dto.TaskAttachmentCreationRequest;
import com.zerodefects.taskmanager.rest.api.dto.TaskAttachmentResponse;
import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskAttachmentFacadeMapper {

    TaskAttachmentModel creationRequestToServiceModel(TaskAttachmentCreationRequest taskAttachmentCreationRequest);

    TaskAttachmentResponse serviceModelToResponse(TaskAttachmentModel taskAttachmentModel);

}
