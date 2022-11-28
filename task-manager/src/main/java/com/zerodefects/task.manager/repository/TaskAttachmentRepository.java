package com.zerodefects.task.manager.repository;

import com.zerodefects.task.manager.repository.entity.TaskAttachment;
import com.zerodefects.task.manager.service.model.TaskAttachmentModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAttachmentRepository extends PagingAndSortingRepository<TaskAttachment, String> {

    List<TaskAttachment> findByTaskId(String taskId);

}
