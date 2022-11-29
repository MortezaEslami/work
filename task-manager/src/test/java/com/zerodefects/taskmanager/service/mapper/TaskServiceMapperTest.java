package com.zerodefects.taskmanager.service.mapper;

import com.zerodefects.taskmanager.repository.entity.Task;
import com.zerodefects.taskmanager.service.model.TaskModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskServiceMapperTest {

    private TaskServiceMapper mapper = Mappers.getMapper(TaskServiceMapper.class);
    private PodamFactory podamFactory = new PodamFactoryImpl();


    @Test
    void serviceModelToEntity() {
        TaskModel model = podamFactory.manufacturePojo(TaskModel.class);
        Task entity = mapper.serviceModelToEntity(model);
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getTitle(), entity.getTitle());
        assertEquals(model.getDescription(), entity.getDescription());
    }

    @Test
    void entityToServiceModel() {
        Task entity = podamFactory.manufacturePojo(Task.class);
        TaskModel model = mapper.entityToServiceModel(entity);
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getTitle(), model.getTitle());
        assertEquals(entity.getDescription(), model.getDescription());
    }
}
