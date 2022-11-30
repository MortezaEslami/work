package com.zerodefects.taskmanager.rest.api.facade;

import com.zerodefects.taskmanager.exception.ResourceNotFoundException;
import com.zerodefects.taskmanager.rest.api.dto.TaskCreationRequest;
import com.zerodefects.taskmanager.rest.api.dto.TaskResponse;
import com.zerodefects.taskmanager.service.TaskServiceImpl;
import com.zerodefects.taskmanager.service.model.TaskModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.WebRequest;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskFacadeTest {

    @InjectMocks
    private TaskFacade facade;

    @Mock
    private TaskServiceImpl service;

    @Mock
    private TaskFacadeMapper mapper;

    @Mock
    private MessageSource messageSource;

    @Mock
    private WebRequest request;

    private PodamFactory podamFactory = new PodamFactoryImpl();

    private String id;

    @Test
    void testCreate() {
        TaskCreationRequest request = podamFactory.manufacturePojo(TaskCreationRequest.class);
        TaskModel model = podamFactory.manufacturePojo(TaskModel.class);
        TaskResponse response = podamFactory.manufacturePojo(TaskResponse.class);

        when(mapper.creationRequestToServiceModel(request)).thenReturn(model);
        when(service.create(model)).thenReturn(model);
        when(mapper.serviceModelToResponse(model)).thenReturn(response);

        TaskResponse modelResponse = facade.create(request);

        verify(mapper, times(1)).creationRequestToServiceModel(request);
        verify(service, times(1)).create(model);
        verify(mapper, times(1)).serviceModelToResponse(model);
        assertEquals(modelResponse, response);
    }

    @Test
    void testUpdate() {
        id = RandomStringUtils.randomAlphanumeric(20);
        TaskCreationRequest request = podamFactory.manufacturePojo(TaskCreationRequest.class);
        TaskModel model = podamFactory.manufacturePojo(TaskModel.class);
        TaskResponse response = podamFactory.manufacturePojo(TaskResponse.class);

        when(mapper.creationRequestToServiceModel(request)).thenReturn(model);
        when(service.update(model)).thenReturn(true);

        facade.update(request, id);

        verify(mapper, times(1)).creationRequestToServiceModel(request);
        verify(service, times(1)).update(model);
    }


    @Test
    void testUpdateWithInvalidId() {
        id = RandomStringUtils.randomAlphanumeric(20);
        TaskCreationRequest requestObject = podamFactory.manufacturePojo(TaskCreationRequest.class);
        TaskModel model = podamFactory.manufacturePojo(TaskModel.class);

        when(mapper.creationRequestToServiceModel(requestObject)).thenReturn(model);
        when(service.update(model)).thenReturn(false);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        assertThrows(ResourceNotFoundException.class, () -> facade.update(requestObject, id));

        verify(mapper, times(1)).creationRequestToServiceModel(requestObject);
        verify(service, times(1)).update(model);
        verify(messageSource, times(1)).getMessage("resource.not.found.for.your.request", null, Locale.ENGLISH);
    }


    @Test
    void testGet() {
        id = RandomStringUtils.randomAlphanumeric(20);
        TaskModel model = podamFactory.manufacturePojo(TaskModel.class);
        TaskResponse response = podamFactory.manufacturePojo(TaskResponse.class);

        when(service.get(id)).thenReturn(model);
        when(mapper.serviceModelToResponse(model)).thenReturn(response);

        TaskResponse modelResponse = facade.get(id);

        verify(service, times(1)).get(id);
        verify(mapper, times(1)).serviceModelToResponse(model);
        assertEquals(modelResponse, response);
    }


    @Test
    void testGetWithInvalidId() {
        id = RandomStringUtils.randomAlphanumeric(20);
        System.out.println(id);

        when(service.get(id)).thenReturn(null);
        when(messageSource.getMessage("resource.not.found.for.your.request", null, Locale.ENGLISH)).thenReturn("test");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        assertThrows(ResourceNotFoundException.class, () -> facade.get(id));

        verify(service, times(1)).get(id);
        verify(messageSource, times(1)).getMessage("resource.not.found.for.your.request", null, Locale.ENGLISH);
    }

    @Test
    void testList() {
        TaskModel model = podamFactory.manufacturePojo(TaskModel.class);
        TaskResponse response = podamFactory.manufacturePojo(TaskResponse.class);

        when(service.list()).thenReturn(List.of(model));
        when(mapper.serviceModelToResponse(model)).thenReturn(response);

        List<TaskResponse> modelResponse = facade.list();

        verify(service, times(1)).list();
        verify(mapper, times(1)).serviceModelToResponse(model);
        assertEquals(modelResponse, List.of(response));
    }


    @Test
    void testSuccessfulDelete() {
        id = RandomStringUtils.randomAlphanumeric(20);

        when(service.delete(id)).thenReturn(true);

        facade.delete(id);

        verify(service, times(1)).delete(id);
    }


    @Test
    void testFailDelete() {
        id = RandomStringUtils.randomAlphanumeric(20);
        when(service.delete(id)).thenReturn(false);
        when(messageSource.getMessage("resource.not.found.for.your.request", null, Locale.ENGLISH)).thenReturn("test");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        assertThrows(ResourceNotFoundException.class, () -> facade.delete(id));

        verify(service, times(1)).delete(id);
        verify(request, times(1)).getLocale();
        verify(messageSource, times(1)).getMessage("resource.not.found.for.your.request", null, Locale.ENGLISH);

    }
}
