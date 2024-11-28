package org.pragadeesh.taskmanagement.util;

import lombok.Getter;

import java.util.Objects;

@Getter
public class JwtResponse {

    private final String token;

    public JwtResponse(String token) {
        this.token = Objects.requireNonNull(token, "token cannot be null");
    }
}
