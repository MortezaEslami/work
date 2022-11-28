package com.zerodefects.task.manager.service.mapper;


import com.zerodefects.task.manager.repository.entity.TaskAttachment;
import com.zerodefects.task.manager.service.model.TaskAttachmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskAttachmentServiceMapper {

    TaskAttachment serviceModelToEntity(TaskAttachmentModel serviceModel);

    TaskAttachmentModel entityToServiceModel(TaskAttachment taskAttachment);
}
