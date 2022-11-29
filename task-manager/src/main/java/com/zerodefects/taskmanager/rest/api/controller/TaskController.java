package com.zerodefects.taskmanager.rest.api.controller;

import com.zerodefects.taskmanager.rest.api.dto.TaskCreationRequest;
import com.zerodefects.taskmanager.rest.api.dto.TaskResponse;
import com.zerodefects.taskmanager.rest.api.facade.TaskFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value = "/api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskFacade facade;

    @RolesAllowed("quality-manager")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated TaskCreationRequest taskCreationRequest) {
        return new ResponseEntity<>(facade.create(taskCreationRequest), HttpStatus.OK);
    }

    @RolesAllowed("quality-manager")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Validated TaskCreationRequest task, @PathVariable String id) {
        facade.update(task, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RolesAllowed({"quality-manager", "worker"})
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(facade.list());
    }

    @RolesAllowed({"quality-manager", "worker"})
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(facade.get(id));
    }


    @RolesAllowed("quality-manager")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        facade.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
