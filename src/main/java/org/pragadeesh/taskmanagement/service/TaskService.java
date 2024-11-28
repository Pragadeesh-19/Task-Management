package org.pragadeesh.taskmanagement.service;

import lombok.RequiredArgsConstructor;

import org.pragadeesh.taskmanagement.Exception.DepartmentNotFoundException;
import org.pragadeesh.taskmanagement.Exception.TaskNotFoundException;
import org.pragadeesh.taskmanagement.Exception.UserNotFoundException;
import org.pragadeesh.taskmanagement.dto.TaskCreateDto;
import org.pragadeesh.taskmanagement.model.Department;
import org.pragadeesh.taskmanagement.model.Task;
import org.pragadeesh.taskmanagement.model.TaskStatus;
import org.pragadeesh.taskmanagement.model.User;
import org.pragadeesh.taskmanagement.repository.DepartmentRepository;
import org.pragadeesh.taskmanagement.repository.TaskRepository;
import org.pragadeesh.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public Task getTaskById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task createTask(TaskCreateDto taskCreateDto) {
        Task task = new Task();
        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setDueDate(taskCreateDto.getDueDate());
        task.setStatus(TaskStatus.PENDING);

        if (taskCreateDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(taskCreateDto.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with Id: " + taskCreateDto.getDepartmentId()));
            task.setDepartment(department);
        }

        if (taskCreateDto.getAssigneduserIds() != null && !taskCreateDto.getAssigneduserIds().isEmpty()) {
            Set<User> users = new HashSet<>();
            for(UUID userId : taskCreateDto.getAssigneduserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
                users.add(user);
            }
            task.setAssignedUsers(users);
        } else {
            task.setAssignedUsers(new HashSet<>());
        }
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(UUID id, TaskCreateDto taskDto) {
        Task existingTask = getTaskById(id);
        
        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setDueDate(taskDto.getDueDate());

        // Update department
        if (taskDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(taskDto.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + taskDto.getDepartmentId()));
            existingTask.setDepartment(department);
        }

        // Update assigned users
        if (taskDto.getAssigneduserIds() != null) {
            Set<User> users = new HashSet<>();
            for (UUID userId : taskDto.getAssigneduserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
                users.add(user);
            }
            existingTask.setAssignedUsers(users);
        }

        return taskRepository.save(existingTask);
    }

    public void deleteTask(UUID id) {
        Task task = getTaskById(id);
        try {
            taskRepository.delete(task);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete task" + e.getMessage());
        }
    }

    public Task markTaskAsCompleted(UUID id) {
        Task task = getTaskById(id);
        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Task is already completed");
        }

        try {
            task.setStatus(TaskStatus.COMPLETED);
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new RuntimeException("Failed to mark task as completed" + e.getMessage());
        }
    }

    @Transactional
    public Task assignUsersToTask(UUID taskId, Set<UUID> userIds) {
        Task task = getTaskById(taskId);
        Set<User> users = new HashSet<>();
        
        for (UUID userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
            users.add(user);
        }
        
        task.setAssignedUsers(users);
        return taskRepository.save(task);
    }

    public Task assignDepartmentToTask(UUID taskId, UUID departmentId) {
        Task task = getTaskById(taskId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + departmentId));
        task.setDepartment(department);
        return taskRepository.save(task);
    }
}
