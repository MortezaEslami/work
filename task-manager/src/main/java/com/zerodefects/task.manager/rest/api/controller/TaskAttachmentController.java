package com.zerodefects.task.manager.rest.api.controller;

import com.zerodefects.task.manager.rest.api.dto.TaskAttachmentCreationRequest;
import com.zerodefects.task.manager.rest.api.dto.TaskAttachmentResponse;
import com.zerodefects.task.manager.rest.api.facade.TaskAttachmentFacade;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value = "/api/v1/task-attachments")
@AllArgsConstructor
public class TaskAttachmentController {

    private final TaskAttachmentFacade facade;

    @RolesAllowed("quality-manager")
    @PostMapping("/upload/task/{id}")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        return new ResponseEntity<>(facade.create(new TaskAttachmentCreationRequest(id, file)), HttpStatus.OK);
    }

    @RolesAllowed({"quality-manager", "worker"})
    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable String id) {
        TaskAttachmentResponse file = facade.get(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }


    @RolesAllowed({"quality-manager", "worker"})
    @GetMapping("/task/{id}")
    public ResponseEntity<?> list(@PathVariable String id) {
        return ResponseEntity.ok(facade.list(id));
    }

    @RolesAllowed("quality-manager")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        facade.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
