package org.pragadeesh.taskmanagement.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.pragadeesh.taskmanagement.model.Task;
import org.pragadeesh.taskmanagement.model.TaskStatus;
import org.pragadeesh.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task getTaskById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        task.setStatus(TaskStatus.PENDING);
        return taskRepository.save(task);
    }

    public Task updateTask(UUID id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatus(updatedTask.getStatus());
        return taskRepository.save(existingTask);
    }

    public void deleteTask(UUID id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    public Task markTaskAsCompleted(UUID id) {
        Task task = getTaskById(id);
        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Task is already completed");
        }

        task.setStatus(TaskStatus.COMPLETED);
        return taskRepository.save(task);
    }
}
