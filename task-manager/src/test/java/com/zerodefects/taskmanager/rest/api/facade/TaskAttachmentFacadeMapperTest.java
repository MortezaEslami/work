package com.zerodefects.taskmanager.rest.api.facade;

import com.zerodefects.taskmanager.rest.api.dto.TaskAttachmentResponse;
import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskAttachmentFacadeMapperTest {

    private TaskAttachmentFacadeMapper mapper = Mappers.getMapper(TaskAttachmentFacadeMapper.class);
    private PodamFactory podamFactory = new PodamFactoryImpl();

    @Test
    void serviceModelToResponseTest() {
        TaskAttachmentModel model = podamFactory.manufacturePojo(TaskAttachmentModel.class);
        TaskAttachmentResponse response = mapper.serviceModelToResponse(model);
        assertEquals(model.getId(), response.getId());
        assertEquals(model.getTaskId(), response.getTaskId());
        assertEquals(model.getName(), response.getName());
        assertEquals(model.getType(), response.getType());
    }
}
