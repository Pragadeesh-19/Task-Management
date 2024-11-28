package org.pragadeesh.taskmanagement.mapper;

import org.pragadeesh.taskmanagement.dto.DepartmentDto;
import org.pragadeesh.taskmanagement.dto.TaskResponseDto;
import org.pragadeesh.taskmanagement.dto.UserDto;
import org.pragadeesh.taskmanagement.model.Department;
import org.pragadeesh.taskmanagement.model.Task;
import org.pragadeesh.taskmanagement.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public TaskResponseDto toDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpDatedAt(task.getUpdatedAt());

        if (task.getDepartment() != null) {
            dto.setDepartment(toDepartmentDto(task.getDepartment()));
        }

        if (task.getAssignedUsers() != null) {
            dto.setAssignedUsers(task.getAssignedUsers().stream()
                    .map(this::toUserDto)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    private DepartmentDto toDepartmentDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        return dto;
    }

    private UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}
