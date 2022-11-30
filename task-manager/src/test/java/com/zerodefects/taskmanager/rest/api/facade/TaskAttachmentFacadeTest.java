package com.zerodefects.taskmanager.rest.api.facade;

import com.zerodefects.taskmanager.rest.api.dto.TaskAttachmentCreationRequest;
import com.zerodefects.taskmanager.service.TaskAttachmentServiceImpl;
import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.WebRequest;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskAttachmentFacadeTest {
    @InjectMocks
    private TaskAttachmentFacade facade;

    @Mock
    private TaskAttachmentServiceImpl service;

    @Mock
    private TaskAttachmentFacadeMapper mapper;

    @Mock
    private MessageSource messageSource;

    @Mock
    private WebRequest request;

    private PodamFactory podamFactory = new PodamFactoryImpl();

    private String id;

    @Test
    void testCreate() {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        TaskAttachmentCreationRequest request = podamFactory.manufacturePojo(TaskAttachmentCreationRequest.class);
        request.setFile(file);
        TaskAttachmentModel model = podamFactory.manufacturePojo(TaskAttachmentModel.class);
        String response = RandomStringUtils.randomAlphanumeric(20);

        when(mapper.creationRequestToServiceModel(request)).thenReturn(model);
        when(service.create(model)).thenReturn(response);

        String modelResponse = facade.create(request);

        verify(mapper, times(1)).creationRequestToServiceModel(request);
        verify(service, times(1)).create(model);
        verify(mapper, times(1)).serviceModelToResponse(model);
        assertEquals(modelResponse, id);
    }
}
