package org.pragadeesh.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.pragadeesh.taskmanagement.Exception.ErrorResponse;
import org.pragadeesh.taskmanagement.dto.TaskCreateDto;
import org.pragadeesh.taskmanagement.dto.TaskResponseDto;
import org.pragadeesh.taskmanagement.mapper.TaskMapper;
import org.pragadeesh.taskmanagement.model.Task;
import org.pragadeesh.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "APIs for managing tasks including CRUD operations")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Operation(
            summary = "Retrieve all tasks",
            description = "Gets a list of all tasks for the authenticated user. Returns an empty list if no tasks exist."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all tasks",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = Task.class)),
                    mediaType = "application/json"
            )
    )
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTask() {
        List<TaskResponseDto> tasks = taskService.getAllTask()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Get task by ID",
            description = "Retrieves a specific task using its UUID. Returns 404 if task is not found."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task found successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@Parameter(description = "UUID of the task", required = true) @PathVariable UUID id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    @Operation(
            summary = "Create a new task",
            description = "Creates a new task with the provided details. The task status is automatically set to PENDING."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskCreateDto taskCreateDto) {
        Task task = taskService.createTask(taskCreateDto);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    @Operation(
            summary = "Update an existing task",
            description = "Updates a task with the provided details. All fields can be updated except the ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @Parameter(description = "UUID of the task to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Updated task details", required = true)
            @RequestBody TaskCreateDto taskCreateDto) {
        Task updatedTask = taskService.updateTask(id, taskCreateDto);
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    @Operation(
            summary = "Delete a task",
            description = "Permanently deletes a task by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "UUID of the task to delete", required = true) @PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Mark task as completed",
            description = "Updates the status of a task to COMPLETED. Returns error if task is already completed."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task marked as completed successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Task is already completed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponseDto> markTaskAsCompleted(
            @Parameter(description = "UUID of the task to mark as completed", required = true) @PathVariable UUID id) {
        Task completedTask = taskService.markTaskAsCompleted(id);
        return ResponseEntity.ok(taskMapper.toDto(completedTask));
    }

    @Operation(summary = "Assign users to task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Task or users not found")
    })
    @PostMapping("/{taskId}/assign-users")
    public ResponseEntity<TaskResponseDto> assignUsersToTask(
            @PathVariable UUID taskId,
            @RequestBody List<UUID> userIds) {
        Task updatedTask = taskService.assignUsersToTask(taskId, new HashSet<>(userIds));
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    @Operation(summary = "Assign department to task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Task or department not found")
    })
    @PostMapping("/{taskId}/assign-department")
    public ResponseEntity<TaskResponseDto> assignDepartmentToTask(
            @PathVariable UUID taskId,
            @RequestBody UUID departmentId) {
        Task updatedTask = taskService.assignDepartmentToTask(taskId, departmentId);
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    
}
