package com.example.Study.Hall.Management.exception;

import com.example.Study.Hall.Management.wrappers.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid username or password");
        response.put("message", ex.getMessage()); // Include exception message
        response.put("status", "401 Unauthorized");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid credentials");
        response.put("message", ex.getMessage()); // Include exception message
        response.put("status", "401 Unauthorized");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Handle Access Denied Exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Access Denied");
        response.put("message", ex.getMessage());
        response.put("status", "403 Forbidden");

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        List<String> errors = List.of(ex.getMessage());
        ResponseWrapper<String> failureResponse = new ResponseWrapper<>(false,errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failureResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("status", "500 Internal Server Error");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
