package com.zerodefects.taskmanager.repository;

import com.zerodefects.taskmanager.repository.entity.Task;
import com.zerodefects.taskmanager.repository.entity.TaskAttachment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class TaskAttachmentRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @Autowired
    private TaskAttachmentRepository repository;

    private PodamFactory podamFactory = new PodamFactoryImpl();
    private TaskAttachment entity;
    private Task entityTask;

    @BeforeEach
    void init() {
        entity = podamFactory.manufacturePojo(TaskAttachment.class);
        entityTask = podamFactory.manufacturePojo(Task.class);
        entity.setTask(entityTask);
        entity.setId(null);
        repository.save(entity);
    }

    @Test
    void saveTestAttachment() {
        entity = podamFactory.manufacturePojo(TaskAttachment.class);
        entity.setId(null);
        repository.save(entity);
        assertNotNull(entity.getId());
    }

    @Test
    void getTestAttachment() {
        Optional<TaskAttachment> byId = repository.findById(entity.getId());
        assertTrue(byId.isPresent());
        TaskAttachment taskAttachment = byId.get();
        assertNotNull(taskAttachment.getId());
        assertNotNull(taskAttachment.getName());
        assertNotNull(taskAttachment.getType());
        assertEquals(entity.getName(), taskAttachment.getName());
    }

    @Test
    void deleteTestAttachment() {
        repository.deleteById(entity.getId());
        Optional<TaskAttachment> byId = repository.findById(entity.getId());
        assertFalse(byId.isPresent());
    }
}
