package com.tietoevry.surest.member.management.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
<<<<<<< HEAD

    private final ErrorMessagesConfig errorMessages;

=======
    private final ErrorMessagesConfig errorMessages;

>>>>>>> 602906156e831d4c39cb12030336b68fdf676fc6
    public GlobalExceptionHandler(ErrorMessagesConfig errorMessages) {
        this.errorMessages = errorMessages;
    }

    @ExceptionHandler(ApiException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ApiException.NotFound ex) {
<<<<<<< HEAD

        // ✅ USE category & key FROM EXCEPTION
        ErrorMessagesConfig.ErrorDetail detail =
                errorMessages.getErrorDetail(ex.getCategory(), ex.getKey());

        ErrorResponse response = new ErrorResponse(
                "NOT_FOUND",
                detail.getSystemMessage(),
                detail.getUserMessage()
        );

=======
        ErrorMessagesConfig.ErrorDetail detail = errorMessages.getErrorDetail("user", "not-found");
        ErrorResponse response = new ErrorResponse("NOT_FOUND", detail.getSystemMessage(), detail.getUserMessage());
>>>>>>> 602906156e831d4c39cb12030336b68fdf676fc6
        return ResponseEntity.status(detail.getStatusCode()).body(response);
    }

    @ExceptionHandler(ApiException.Unauthorized.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(ApiException.Unauthorized ex) {
<<<<<<< HEAD

        ErrorMessagesConfig.ErrorDetail detail =
                errorMessages.getErrorDetail(ex.getCategory(), ex.getKey());

        ErrorResponse response = new ErrorResponse(
                "UNAUTHORIZED",
                detail.getSystemMessage(),
                detail.getUserMessage()
        );

=======
        ErrorMessagesConfig.ErrorDetail detail = errorMessages.getErrorDetail("user", "invalid-credentials");
        ErrorResponse response = new ErrorResponse("UNAUTHORIZED", detail.getSystemMessage(), detail.getUserMessage());
>>>>>>> 602906156e831d4c39cb12030336b68fdf676fc6
        return ResponseEntity.status(detail.getStatusCode()).body(response);
    }

    @ExceptionHandler(ApiException.Conflict.class)
    public ResponseEntity<ErrorResponse> handleConflict(ApiException.Conflict ex) {
<<<<<<< HEAD

        ErrorMessagesConfig.ErrorDetail detail =
                errorMessages.getErrorDetail(ex.getCategory(), ex.getKey());

        ErrorResponse response = new ErrorResponse(
                "CONFLICT",
                detail.getSystemMessage(),
                detail.getUserMessage()
        );

=======
        ErrorMessagesConfig.ErrorDetail detail = errorMessages.getErrorDetail("member", "email-exists");
        ErrorResponse response = new ErrorResponse("CONFLICT", detail.getSystemMessage(), detail.getUserMessage());
>>>>>>> 602906156e831d4c39cb12030336b68fdf676fc6
        return ResponseEntity.status(detail.getStatusCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
<<<<<<< HEAD
                .collect(Collectors.toMap(
                        f -> f.getField(),
                        f -> f.getDefaultMessage()
                ));
=======
                .collect(Collectors.toMap(f -> f.getField(), f -> f.getDefaultMessage()));
>>>>>>> 602906156e831d4c39cb12030336b68fdf676fc6
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
<<<<<<< HEAD

        ErrorMessagesConfig.ErrorDetail detail =
                errorMessages.getErrorDetail("server", "internal-error");

        ErrorResponse response = new ErrorResponse(
                "INTERNAL_ERROR",
                detail.getSystemMessage(),
                detail.getUserMessage()
        );

=======
        ErrorMessagesConfig.ErrorDetail detail = errorMessages.getErrorDetail("server", "internal-error");
        ErrorResponse response = new ErrorResponse("INTERNAL_ERROR", detail.getSystemMessage(), detail.getUserMessage());
>>>>>>> 602906156e831d4c39cb12030336b68fdf676fc6
        return ResponseEntity.status(detail.getStatusCode()).body(response);
    }
}
