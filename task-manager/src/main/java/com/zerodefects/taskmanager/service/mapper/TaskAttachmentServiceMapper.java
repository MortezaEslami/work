package com.zerodefects.taskmanager.service.mapper;


import com.zerodefects.taskmanager.repository.entity.TaskAttachment;
import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskAttachmentServiceMapper {

    TaskAttachment serviceModelToEntity(TaskAttachmentModel serviceModel);

    TaskAttachmentModel entityToServiceModel(TaskAttachment taskAttachment);
}
