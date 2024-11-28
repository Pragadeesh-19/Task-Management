package org.pragadeesh.taskmanagement.dto;

import java.util.UUID;

import org.pragadeesh.taskmanagement.model.Role;

import lombok.Data;

@Data
public class UserDto {
    
    private UUID id;
    private String username;
    private Role role;
}
