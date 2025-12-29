package com.tietoevry.surest.member.management.controller;

import com.tietoevry.surest.member.management.dto.AuthResponse;
import com.tietoevry.surest.member.management.dto.LoginRequest;
import com.tietoevry.surest.member.management.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        String token = authService.login(req.username, req.password);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

