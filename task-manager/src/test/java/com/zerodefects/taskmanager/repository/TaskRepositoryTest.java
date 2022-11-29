package com.zerodefects.taskmanager.repository;

import com.zerodefects.taskmanager.repository.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class TaskRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @Autowired
    private TaskRepository repository;

    PodamFactory podamFactory = new PodamFactoryImpl();
    private Task entity;

    @BeforeEach
    void init() {
        entity = podamFactory.manufacturePojo(Task.class);
        entity.setId(null);
        repository.save(entity);
    }

    @Test
    void saveTest() {
        entity = podamFactory.manufacturePojo(Task.class);
        entity.setId(null);
        repository.save(entity);
        assertNotNull(entity.getId());
    }

    @Test
    void updateTest() {
        Optional<Task> entityOptional = repository.findById(entity.getId());
        assertTrue(entityOptional.isPresent());
        Task task = entityOptional.get();
        assertNotNull(task);
        task.setTitle("update");
        repository.save(task);

        Optional<Task> updatedTaskOptional = repository.findById(task.getId());
        assertTrue(updatedTaskOptional.isPresent());
        Task updatedTask = updatedTaskOptional.get();

        assertEquals(task.getTitle(), updatedTask.getTitle());
    }

    @Test
    void listTest() {
        Iterable<Task> all = repository.findAll();
        all.forEach(item -> {
            assertNotNull(item.getId());
            assertNotNull(item.getTitle());
            assertNotNull(item.getDescription());
        });
    }

    @Test
    void getTest() {
        Optional<Task> byId = repository.findById(entity.getId());
        assertTrue(byId.isPresent());
        Task task = byId.get();
        assertNotNull(task.getId());
        assertNotNull(task.getTitle());
        assertEquals(entity.getTitle(), task.getTitle());
    }

    @Test
    void deleteTest() {
        repository.deleteById(entity.getId());
        Optional<Task> byId = repository.findById(entity.getId());
        assertFalse(byId.isPresent());
    }
}
