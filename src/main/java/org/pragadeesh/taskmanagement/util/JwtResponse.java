package org.pragadeesh.taskmanagement.util;

import lombok.Getter;
import org.pragadeesh.taskmanagement.model.User;

import java.util.Objects;

@Getter
public class JwtResponse {

    private final String token;
    private final User user;

    public JwtResponse(String token, User user) {
        this.token = Objects.requireNonNull(token, "token cannot be null");
        this.user = Objects.requireNonNull(user, "User cannot be null");
    }
}
