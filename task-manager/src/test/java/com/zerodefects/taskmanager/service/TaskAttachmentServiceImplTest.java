package com.zerodefects.taskmanager.service;

import com.zerodefects.taskmanager.repository.TaskAttachmentRepository;
import com.zerodefects.taskmanager.repository.entity.TaskAttachment;
import com.zerodefects.taskmanager.service.mapper.TaskAttachmentServiceMapper;
import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskAttachmentServiceImplTest {

    @Spy
    @InjectMocks
    private TaskAttachmentServiceImpl service;

    @Mock
    private TaskAttachmentRepository repository;
    @Mock
    private TaskAttachmentServiceMapper mapper;
    PodamFactory podamFactory = new PodamFactoryImpl();


    @Test
    void createTest() {
        TaskAttachmentModel mockInputModel = podamFactory.manufacturePojo(TaskAttachmentModel.class);
        TaskAttachment entity = podamFactory.manufacturePojo(TaskAttachment.class);

        when(mapper.serviceModelToEntity(mockInputModel)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        String result = service.create(mockInputModel);

        verify(mapper, times(1)).serviceModelToEntity(mockInputModel);
        verify(repository, times(1)).save(entity);
    }

    @Test
    void getTest() {
        TaskAttachmentModel mockInputModel = podamFactory.manufacturePojo(TaskAttachmentModel.class);
        TaskAttachment entity = podamFactory.manufacturePojo(TaskAttachment.class);
        TaskAttachmentModel mockOutputModel = podamFactory.manufacturePojo(TaskAttachmentModel.class);

        when(repository.findById(mockInputModel.getId())).thenReturn(Optional.of(entity));
        when(mapper.entityToServiceModel(entity)).thenReturn(mockOutputModel);

        TaskAttachmentModel result = service.get(mockInputModel.getId());

        verify(repository, times(1)).findById(mockInputModel.getId());
        verify(mapper, times(1)).entityToServiceModel(entity);
    }

    @Test
    void testList() {
        String id = "1";
        TaskAttachment entity = podamFactory.manufacturePojo(TaskAttachment.class);
        TaskAttachmentModel mockOutputModel = podamFactory.manufacturePojo(TaskAttachmentModel.class);

        when(repository.findByTaskId(id)).thenReturn(List.of(entity));
        when(mapper.entityToServiceModel(entity)).thenReturn(mockOutputModel);
        List<TaskAttachmentModel> result = service.list(id);

        verify(repository, times(1)).findByTaskId(id);
        verify(mapper, times(1)).entityToServiceModel(entity);
    }

    @Test
    void testDelete() {
        TaskAttachmentModel mockInputModel = podamFactory.manufacturePojo(TaskAttachmentModel.class);
        TaskAttachment entity = podamFactory.manufacturePojo(TaskAttachment.class);

        when(repository.findById(mockInputModel.getId())).thenReturn(Optional.of(entity));

        boolean result = service.delete(mockInputModel.getId());

        verify(repository, times(1)).findById(mockInputModel.getId());

    }
}
