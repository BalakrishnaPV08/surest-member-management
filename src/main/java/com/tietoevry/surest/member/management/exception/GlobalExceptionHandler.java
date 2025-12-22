package com.tietoevry.surest.member.management.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ErrorMessagesConfig errorMessages;

    public GlobalExceptionHandler(ErrorMessagesConfig errorMessages) {
        this.errorMessages = errorMessages;
    }

    @ExceptionHandler(ApiException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ApiException.NotFound ex) {
        ErrorMessagesConfig.ErrorDetail detail = errorMessages.getErrorDetail("user", "not-found");
        ErrorResponse response = new ErrorResponse("NOT_FOUND", detail.getSystemMessage(), detail.getUserMessage());
        return ResponseEntity.status(detail.getStatusCode()).body(response);
    }

    @ExceptionHandler(ApiException.Unauthorized.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(ApiException.Unauthorized ex) {
        ErrorMessagesConfig.ErrorDetail detail = errorMessages.getErrorDetail("user", "invalid-credentials");
        ErrorResponse response = new ErrorResponse("UNAUTHORIZED", detail.getSystemMessage(), detail.getUserMessage());
        return ResponseEntity.status(detail.getStatusCode()).body(response);
    }

    @ExceptionHandler(ApiException.Conflict.class)
    public ResponseEntity<ErrorResponse> handleConflict(ApiException.Conflict ex) {
        ErrorMessagesConfig.ErrorDetail detail = errorMessages.getErrorDetail("member", "email-exists");
        ErrorResponse response = new ErrorResponse("CONFLICT", detail.getSystemMessage(), detail.getUserMessage());
        return ResponseEntity.status(detail.getStatusCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(f -> f.getField(), f -> f.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorMessagesConfig.ErrorDetail detail = errorMessages.getErrorDetail("server", "internal-error");
        ErrorResponse response = new ErrorResponse("INTERNAL_ERROR", detail.getSystemMessage(), detail.getUserMessage());
        return ResponseEntity.status(detail.getStatusCode()).body(response);
    }
}
