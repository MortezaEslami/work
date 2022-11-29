package com.zerodefects.taskmanager.service.mapper;


import com.zerodefects.taskmanager.repository.entity.TaskAttachment;
import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskAttachmentServiceMapper {

    @Mappings({

            @Mapping(source = "taskId", target = "task.id")
    })
    TaskAttachment serviceModelToEntity(TaskAttachmentModel serviceModel);

    @Mappings({

            @Mapping(source = "task.id", target = "taskId")
    })
    TaskAttachmentModel entityToServiceModel(TaskAttachment taskAttachment);
}
