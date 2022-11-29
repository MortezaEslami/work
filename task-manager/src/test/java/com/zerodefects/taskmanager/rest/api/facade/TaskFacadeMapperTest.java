package com.zerodefects.taskmanager.rest.api.facade;

import com.zerodefects.taskmanager.rest.api.dto.TaskCreationRequest;
import com.zerodefects.taskmanager.rest.api.dto.TaskResponse;
import com.zerodefects.taskmanager.service.model.TaskModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskFacadeMapperTest {

    private TaskFacadeMapper mapper = Mappers.getMapper(TaskFacadeMapper.class);
    private PodamFactory podamFactory = new PodamFactoryImpl();


    @Test
    void creationRequestToServiceModelTest() {
        TaskCreationRequest request = podamFactory.manufacturePojo(TaskCreationRequest.class);
        TaskModel model = mapper.creationRequestToServiceModel(request);
        assertEquals(request.getTitle(), model.getTitle());
        assertEquals(request.getDescription(), model.getDescription());
    }

    @Test
    void serviceModelToResponseTest() {
        TaskModel model = podamFactory.manufacturePojo(TaskModel.class);
        TaskResponse response = mapper.serviceModelToResponse(model);
        assertEquals(model.getId(), response.getId());
        assertEquals(model.getTitle(), response.getTitle());
        assertEquals(model.getDescription(), response.getDescription());
    }
}
