package com.tietoevry.surest.member.management.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.NotFound.class)
    public ResponseEntity<?> handleNotFound(ApiException.NotFound ex) { return ResponseEntity.status(404).body(Map.of("message", ex.getMessage())); }

    @ExceptionHandler(ApiException.Conflict.class)
    public ResponseEntity<?> handleConflict(ApiException.Conflict ex) { return ResponseEntity.status(409).body(Map.of("message", ex.getMessage())); }

    @ExceptionHandler(ApiException.Unauthorized.class)
    public ResponseEntity<?> handleUnauthorized(ApiException.Unauthorized ex) { return ResponseEntity.status(401).body(Map.of("message", ex.getMessage())); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(f -> f.getField(), f -> f.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(500).body(Map.of("message", "internal server error"));
    }
}

