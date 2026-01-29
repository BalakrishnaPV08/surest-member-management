package com.tietoevry.surest.member.management.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;
    private ErrorMessagesConfig errorMessagesConfig;

    @BeforeEach
    void setup() {
        errorMessagesConfig = Mockito.mock(ErrorMessagesConfig.class);

        GlobalExceptionHandler handler =
                new GlobalExceptionHandler(errorMessagesConfig);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(handler)
                .build();
    }

    // ---------------- Dummy Controller ----------------
    @RestController
    static class TestController {

        @GetMapping("/not-found")
        void notFound() {
            throw new ApiException.NotFound("user", "not-found");
        }

        @GetMapping("/unauthorized")
        void unauthorized() {
            throw new ApiException.Unauthorized("user", "invalid-credentials");
        }

        @GetMapping("/conflict")
        void conflict() {
            throw new ApiException.Conflict("member", "email-exists");
        }

        @PostMapping("/validate")
        void validate(@Valid @RequestBody TestRequest req) {}

        @GetMapping("/error")
        void error() {
            throw new RuntimeException("boom");
        }
    }

    static class TestRequest {
        @NotBlank
        public String name;
    }

    // ---------------- Tests ----------------

    @Test
    void handleNotFound_shouldReturn404() throws Exception {
        mockError("user", "not-found", 404);

        mockMvc.perform(get("/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void handleUnauthorized_shouldReturn401() throws Exception {
        mockError("user", "invalid-credentials", 401);

        mockMvc.perform(get("/unauthorized"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"));
    }

    @Test
    void handleConflict_shouldReturn409() throws Exception {
        mockError("member", "email-exists", 409);

        mockMvc.perform(get("/conflict"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("CONFLICT"));
    }

    @Test
    void handleValidation_shouldReturn400() throws Exception {
        mockMvc.perform(post("/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void handleGenericException_shouldReturn500() throws Exception {
        mockError("server", "internal-error", 500);

        mockMvc.perform(get("/error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"));
    }

    // ---------------- Helper ----------------
    private void mockError(String category, String key, int status) {
        when(errorMessagesConfig.getErrorDetail(category, key))
                .thenReturn(new ErrorMessagesConfig.ErrorDetail(
                        status,
                        "system-msg",
                        "user-msg"
                ));
    }
}
