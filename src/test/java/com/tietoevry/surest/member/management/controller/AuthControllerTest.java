package com.tietoevry.surest.member.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tietoevry.surest.member.management.config.TestSecurityConfig;
import com.tietoevry.surest.member.management.dto.LoginRequest;
import com.tietoevry.surest.member.management.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AuthController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.tietoevry.surest.member.management.config.SecurityConfig.class,
                        com.tietoevry.surest.member.management.config.JwtAuthFilter.class
                }
        )
)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @Test
    void login_withValidCredentials_returns200AndToken() throws Exception {
        LoginRequest req = new LoginRequest();
        req.username = "testuser";
        req.password = "password123";

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlciJ9.signature";

        when(authService.login("testuser", "password123")).thenReturn(token);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void login_withMissingUsername_returns400() throws Exception {
        LoginRequest req = new LoginRequest();
        req.username = null;
        req.password = "password123";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_withMissingPassword_returns400() throws Exception {
        LoginRequest req = new LoginRequest();
        req.username = "testuser";
        req.password = null;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_withEmptyUsername_returns400() throws Exception {
        LoginRequest req = new LoginRequest();
        req.username = "";
        req.password = "password123";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_withEmptyPassword_returns400() throws Exception {
        LoginRequest req = new LoginRequest();
        req.username = "testuser";
        req.password = "";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}
