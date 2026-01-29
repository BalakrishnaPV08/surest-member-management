package com.tietoevry.surest.member.management.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void noArgsConstructor_shouldCreateEmptyObject() {
        ErrorResponse response = new ErrorResponse();

        assertNull(response.code);
        assertNull(response.systemMessage);
        assertNull(response.userMessage);
    }

    @Test
    void allArgsConstructor_shouldSetAllFields() {
        ErrorResponse response = new ErrorResponse(
                "NOT_FOUND",
                "User not found in database",
                "User does not exist"
        );

        assertEquals("NOT_FOUND", response.code);
        assertEquals("User not found in database", response.systemMessage);
        assertEquals("User does not exist", response.userMessage);
    }

    @Test
    void fieldsShouldBeMutable() {
        ErrorResponse response = new ErrorResponse();

        response.code = "BAD_REQUEST";
        response.systemMessage = "Validation failed";
        response.userMessage = "Invalid input provided";

        assertEquals("BAD_REQUEST", response.code);
        assertEquals("Validation failed", response.systemMessage);
        assertEquals("Invalid input provided", response.userMessage);
    }

    @Test
    void objectShouldNotBeNull() {
        ErrorResponse response = new ErrorResponse();

        assertNotNull(response);
    }
}
