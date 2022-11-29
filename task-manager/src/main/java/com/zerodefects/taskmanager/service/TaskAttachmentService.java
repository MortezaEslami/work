package com.zerodefects.taskmanager.service;

import com.zerodefects.taskmanager.service.model.TaskAttachmentModel;

import java.util.List;

public interface TaskAttachmentService {
    String create(TaskAttachmentModel serviceModel);

    TaskAttachmentModel get(String id);

    boolean delete(String id);

    List<TaskAttachmentModel> list(String id);
}
