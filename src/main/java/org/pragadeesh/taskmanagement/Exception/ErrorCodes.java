package org.pragadeesh.taskmanagement.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodes {
    TASK_NOT_FOUND(404, "Task not found"),
    TASK_ALREADY_COMPLETED(400, "Task is already completed"),
    USER_ALREADY_EXISTS(409, "User already exists"),
    INVALID_CREDENTIALS(401, "Invalid credentials"),
    ACCESS_DENIED(403, "Access denied"),
    VALIDATION_ERROR(400, "Validation error"),
    INVALID_TOKEN(401, "Invalid or expired token"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),

    DEPARTMENT_NOT_FOUND(404, "Department not found"), 
    DEPARTMENT_ALREADY_EXISTS(409, "Department already exists"),
    INVALID_ASSIGNMENT(400, "Invalid assignment request"),
    USER_NOT_FOUND(404, "User not found"),
    INVALID_USER_ASSIGNMENT(400, "Invalid user assignment");


    private final int status;
    private final String message;

}
