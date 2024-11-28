package org.pragadeesh.taskmanagement.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class TaskCreateDto {
    
    private String title;
    private String description;
    private LocalDate dueDate;
    private UUID departmentId;
    private Set<UUID> assigneduserIds;
}
