package org.pragadeesh.taskmanagement.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class DepartmentDto {
    
    private UUID id;
    private String name;
    private String description;
}
