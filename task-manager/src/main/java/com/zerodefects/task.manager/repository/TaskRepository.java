package com.zerodefects.task.manager.repository;

import com.zerodefects.task.manager.repository.entity.Task;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, String> {

}
