package org.pragadeesh.taskmanagement.task;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pragadeesh.taskmanagement.model.Task;
import org.pragadeesh.taskmanagement.model.TaskStatus;
import org.pragadeesh.taskmanagement.repository.TaskRepository;
import org.pragadeesh.taskmanagement.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setStatus(TaskStatus.PENDING);
    }

    @Test
    public void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> tasks = taskService.getAllTask();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(task.getId());

        assertNotNull(foundTask);
        assertEquals(task.getId(), foundTask.getId());
        verify(taskRepository, times(1)).findById(task.getId());
    }

    @Test
    public void testGetTaskById_NotFound() {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(UUID.randomUUID()));
        verify(taskRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertNotNull("Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdatedTask() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");
        updatedTask.setDueDate(LocalDate.now().plusDays(10));
        updatedTask.setStatus(TaskStatus.IN_PROGRESS);

        Task result = taskService.updateTask(task.getId(), updatedTask);

        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());

        verify(taskRepository, times(1)).save(task); // called only once
    }

    @Test
    public void testUpdateTask_NotFound() {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Task updatedTask = new Task();
        assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(UUID.randomUUID(), updatedTask));

        verify(taskRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void testDeleteTask() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(task.getId());

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    public void testDeleteTask_NotFound() {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.deleteTask(UUID.randomUUID()));

        verify(taskRepository, times(1)).findById(UUID.randomUUID());
    }

    @Test
    public void testMarkTestCompleted() {
        task.setStatus(TaskStatus.IN_PROGRESS);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task completedTask = taskService.markTaskAsCompleted(task.getId());

        assertEquals(TaskStatus.COMPLETED, completedTask.getStatus());

        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testMarkTaskAsCompleted_AlreadyCompleted() {
        task.setStatus(TaskStatus.COMPLETED);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        assertThrows(IllegalStateException.class, () -> taskService.markTaskAsCompleted(task.getId()));

        verify(taskRepository, never()).save(any(Task.class));
    }
}
