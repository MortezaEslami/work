package com.zerodefects.task.manager.service;

import com.zerodefects.task.manager.service.model.TaskAttachmentModel;
import com.zerodefects.task.manager.service.model.TaskModel;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface TaskAttachmentService {
    String create(TaskAttachmentModel serviceModel);

    TaskAttachmentModel get(String id);

    boolean delete(String id);

    List<TaskAttachmentModel> list(String id);
}
