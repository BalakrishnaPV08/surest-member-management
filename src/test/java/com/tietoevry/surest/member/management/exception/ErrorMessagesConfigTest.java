package com.tietoevry.surest.member.management.exception;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessagesConfigTest {

    @Test
    void defaultErrorDetail_shouldBeReturnedWhenCategoryNotFound() {
        ErrorMessagesConfig config = new ErrorMessagesConfig();

        ErrorMessagesConfig.ErrorDetail detail =
                config.getErrorDetail("user", "not-found");

        assertNotNull(detail);
        assertEquals(500, detail.getStatusCode());
        assertEquals("An error occurred", detail.getUserMessage());
        assertEquals("Internal server error", detail.getSystemMessage());
    }

    @Test
    void defaultErrorDetail_shouldBeReturnedWhenKeyNotFound() {
        ErrorMessagesConfig config = new ErrorMessagesConfig();

        Map<String, Map<String, ErrorMessagesConfig.ErrorDetail>> messages =
                new HashMap<>();

        messages.put("user", new HashMap<>());

        config.setMessages(messages);

        ErrorMessagesConfig.ErrorDetail detail =
                config.getErrorDetail("user", "invalid-key");

        assertEquals(500, detail.getStatusCode());
        assertEquals("An error occurred", detail.getUserMessage());
        assertEquals("Internal server error", detail.getSystemMessage());
    }

    @Test
    void shouldReturnConfiguredErrorDetail() {
        ErrorMessagesConfig config = new ErrorMessagesConfig();

        ErrorMessagesConfig.ErrorDetail expected =
                new ErrorMessagesConfig.ErrorDetail(
                        404,
                        "User not found",
                        "No user exists with given id"
                );

        Map<String, ErrorMessagesConfig.ErrorDetail> userErrors =
                new HashMap<>();
        userErrors.put("not-found", expected);

        Map<String, Map<String, ErrorMessagesConfig.ErrorDetail>> messages =
                new HashMap<>();
        messages.put("user", userErrors);

        config.setMessages(messages);

        ErrorMessagesConfig.ErrorDetail actual =
                config.getErrorDetail("user", "not-found");

        assertEquals(404, actual.getStatusCode());
        assertEquals("User not found", actual.getUserMessage());
        assertEquals("No user exists with given id", actual.getSystemMessage());
    }

    @Test
    void getMessages_and_setMessages_shouldWork() {
        ErrorMessagesConfig config = new ErrorMessagesConfig();

        Map<String, Map<String, ErrorMessagesConfig.ErrorDetail>> messages =
                new HashMap<>();

        config.setMessages(messages);

        assertSame(messages, config.getMessages());
    }

    // ---------- ErrorDetail tests ----------

    @Test
    void errorDetail_noArgsConstructor_shouldCreateEmptyObject() {
        ErrorMessagesConfig.ErrorDetail detail =
                new ErrorMessagesConfig.ErrorDetail();

        detail.setStatusCode(400);
        detail.setUserMessage("Bad request");
        detail.setSystemMessage("Validation failed");

        assertEquals(400, detail.getStatusCode());
        assertEquals("Bad request", detail.getUserMessage());
        assertEquals("Validation failed", detail.getSystemMessage());
    }

    @Test
    void errorDetail_allArgsConstructor_shouldSetAllFields() {
        ErrorMessagesConfig.ErrorDetail detail =
                new ErrorMessagesConfig.ErrorDetail(
                        401,
                        "Unauthorized",
                        "Invalid credentials"
                );

        assertEquals(401, detail.getStatusCode());
        assertEquals("Unauthorized", detail.getUserMessage());
        assertEquals("Invalid credentials", detail.getSystemMessage());
    }
}
