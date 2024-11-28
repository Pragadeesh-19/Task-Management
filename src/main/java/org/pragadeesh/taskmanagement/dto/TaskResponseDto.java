package org.pragadeesh.taskmanagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.pragadeesh.taskmanagement.model.TaskStatus;

import lombok.Data;

@Data
public class TaskResponseDto {
    
    private UUID id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private DepartmentDto department;
    private Set<UserDto> assignedUsers;
    private LocalDateTime createdAt;
    private LocalDateTime upDatedAt;

}
