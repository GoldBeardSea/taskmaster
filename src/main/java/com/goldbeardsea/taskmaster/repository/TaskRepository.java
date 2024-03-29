package com.goldbeardsea.taskmaster.repository;

import com.goldbeardsea.taskmaster.model.Task;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

@EnableScan
public interface TaskRepository extends CrudRepository<Task, String> {
    Optional<Task> findById(String id);
    ArrayList<Task> findByAssignee(String assignee);
}