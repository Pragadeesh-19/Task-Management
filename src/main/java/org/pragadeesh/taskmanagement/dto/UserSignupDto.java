package org.pragadeesh.taskmanagement.dto;

import lombok.Data;
import org.pragadeesh.taskmanagement.model.Role;

@Data
public class UserSignupDto {

    private String username;
    private String password;
    private Role role;
}
