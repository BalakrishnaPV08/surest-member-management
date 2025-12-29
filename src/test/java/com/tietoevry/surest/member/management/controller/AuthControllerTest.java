package com.tietoevry.surest.member.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tietoevry.surest.member.management.dto.LoginRequest;
import com.tietoevry.surest.member.management.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;
    private AuthService authService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        authService = Mockito.mock(AuthService.class);
        objectMapper = new ObjectMapper();

        AuthController controller =
                new AuthController(authService, null, null, null, null);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void login_withValidCredentials_returns200AndToken() throws Exception {

        when(authService.login("testuser", "password123"))
                .thenReturn("jwt-token");

        LoginRequest req = new LoginRequest();
        req.username = "testuser";
        req.password = "password123";

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void login_withMissingUsername_returns400() throws Exception {
        LoginRequest req = new LoginRequest();
        req.password = "password123";

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}
