package com.zerodefects.taskmanager.service;

import com.zerodefects.taskmanager.repository.TaskRepository;
import com.zerodefects.taskmanager.repository.entity.Task;
import com.zerodefects.taskmanager.service.mapper.TaskServiceMapper;
import com.zerodefects.taskmanager.service.model.TaskModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Spy
    @InjectMocks
    private TaskServiceImpl service;

    @Mock
    private TaskRepository repository;
    @Mock
    private TaskServiceMapper mapper;
    PodamFactory podamFactory = new PodamFactoryImpl();


    @Test
    void createTest() {
        TaskModel mockInputModel = podamFactory.manufacturePojo(TaskModel.class);
        Task entity = podamFactory.manufacturePojo(Task.class);
        TaskModel mockOutputModel = podamFactory.manufacturePojo(TaskModel.class);

        when(mapper.serviceModelToEntity(mockInputModel)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.entityToServiceModel(entity)).thenReturn(mockOutputModel);
        Mockito.doReturn("test").when(service).getCurrentUserUsername();


        TaskModel model = service.create(mockInputModel);

        verify(mapper, times(1)).serviceModelToEntity(mockInputModel);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).entityToServiceModel(entity);
        assertEquals(mockOutputModel, model);
    }

    @Test
    void updateTest() {
        TaskModel mockInputModel = podamFactory.manufacturePojo(TaskModel.class);
        Task entity = podamFactory.manufacturePojo(Task.class);

        when(repository.findById(mockInputModel.getId())).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        boolean result = service.update(mockInputModel);

        verify(repository, times(1)).findById(mockInputModel.getId());
        verify(repository, times(1)).save(entity);
        assertTrue(result);
    }

    @Test
    void getTest() {
        TaskModel mockInputModel = podamFactory.manufacturePojo(TaskModel.class);
        Task entity = podamFactory.manufacturePojo(Task.class);
        TaskModel mockOutputModel = podamFactory.manufacturePojo(TaskModel.class);

        when(repository.findById(mockInputModel.getId())).thenReturn(Optional.of(entity));
        when(mapper.entityToServiceModel(entity)).thenReturn(mockOutputModel);
        TaskModel result = service.get(mockInputModel.getId());

        verify(repository, times(1)).findById(mockInputModel.getId());
        verify(mapper, times(1)).entityToServiceModel(entity);
    }

    @Test
    void testList() {
        Task entity = podamFactory.manufacturePojo(Task.class);
        TaskModel mockOutputModel = podamFactory.manufacturePojo(TaskModel.class);

        when(repository.findAllByOrderByCreatedDateDesc()).thenReturn(List.of(entity));
        when(mapper.entityToServiceModel(entity)).thenReturn(mockOutputModel);
        List<TaskModel> result = service.list();

        verify(repository, times(1)).findAllByOrderByCreatedDateDesc();
        verify(mapper, times(1)).entityToServiceModel(entity);
    }

    @Test
    void testDelete() {
        TaskModel mockInputModel = podamFactory.manufacturePojo(TaskModel.class);
        Task entity = podamFactory.manufacturePojo(Task.class);

        when(repository.findById(mockInputModel.getId())).thenReturn(Optional.of(entity));
        boolean result = service.delete(mockInputModel.getId());

        verify(repository, times(1)).findById(mockInputModel.getId());

    }
}
