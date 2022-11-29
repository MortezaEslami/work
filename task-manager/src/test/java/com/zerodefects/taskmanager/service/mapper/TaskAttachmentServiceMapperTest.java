package com.zerodefects.taskmanager.service.mapper;

import com.zerodefects.taskmanager.repository.entity.TaskAttachment;
import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskAttachmentServiceMapperTest {

    private TaskAttachmentServiceMapper mapper = Mappers.getMapper(TaskAttachmentServiceMapper.class);
    private PodamFactory podamFactory = new PodamFactoryImpl();


    @Test
    void serviceModelToEntity() {
        TaskAttachmentModel model = podamFactory.manufacturePojo(TaskAttachmentModel.class);
        TaskAttachment entity = mapper.serviceModelToEntity(model);
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getTaskId(), entity.getTask().getId());
        assertEquals(model.getType(), entity.getType());
    }

    @Test
    void entityToServiceModel() {
        TaskAttachment entity = podamFactory.manufacturePojo(TaskAttachment.class);
        TaskAttachmentModel model = mapper.entityToServiceModel(entity);
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getTask().getId(), model.getTaskId());
        assertEquals(entity.getName(), model.getName());
        assertEquals(entity.getType(), model.getType());
    }
}
