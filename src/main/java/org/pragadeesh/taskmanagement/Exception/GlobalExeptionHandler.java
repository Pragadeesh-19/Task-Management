package org.pragadeesh.taskmanagement.Exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExeptionHandler {


    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.TASK_NOT_FOUND.getStatus(),
            ErrorCodes.TASK_NOT_FOUND.name(),
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.TASK_NOT_FOUND.name()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskAlreadyCompletedException.class)
    public ResponseEntity<ErrorResponse> handleTaskAlreadyCompletedException(TaskAlreadyCompletedException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.TASK_ALREADY_COMPLETED.getStatus(),
            ErrorCodes.TASK_ALREADY_COMPLETED.name(),
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.TASK_ALREADY_COMPLETED.name()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.USER_ALREADY_EXISTS.getStatus(),
            ErrorCodes.USER_ALREADY_EXISTS.name(),
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.USER_ALREADY_EXISTS.name()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.ACCESS_DENIED.getStatus(),
            ErrorCodes.ACCESS_DENIED.name(),
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.ACCESS_DENIED.name()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.INVALID_CREDENTIALS.getStatus(),
            ErrorCodes.INVALID_CREDENTIALS.name(),
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.INVALID_CREDENTIALS.name()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.INVALID_TOKEN.getStatus(),
            ErrorCodes.INVALID_TOKEN.name(),
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.INVALID_TOKEN.name()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.VALIDATION_ERROR.getStatus(),
            "Validation failed: " + errors,
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.VALIDATION_ERROR.name()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.INTERNAL_SERVER_ERROR.getStatus(),
            ErrorCodes.INTERNAL_SERVER_ERROR.name(),
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.INTERNAL_SERVER_ERROR.name()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(
            DepartmentNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                ErrorCodes.DEPARTMENT_NOT_FOUND.getStatus(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI(),
                ErrorCodes.DEPARTMENT_NOT_FOUND.name()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.INVALID_ASSIGNMENT.getStatus(),
            ex.getMessage(),
            LocalDateTime.now(),
            request.getRequestURI(),
            ErrorCodes.INVALID_ASSIGNMENT.name()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                500,
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI(),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    
}
