package org.pragadeesh.taskmanagement.repository;

import org.pragadeesh.taskmanagement.model.Task;
import org.pragadeesh.taskmanagement.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByStatus(TaskStatus status);
}
