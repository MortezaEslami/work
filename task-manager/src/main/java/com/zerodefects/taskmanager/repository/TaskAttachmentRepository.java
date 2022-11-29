package com.zerodefects.taskmanager.repository;

import com.zerodefects.taskmanager.repository.entity.TaskAttachment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAttachmentRepository extends PagingAndSortingRepository<TaskAttachment, String> {

    List<TaskAttachment> findByTaskId(String taskId);

}
