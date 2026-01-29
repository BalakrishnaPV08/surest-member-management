package com.tietoevry.surest.member.management.controller;

import com.tietoevry.surest.member.management.config.JwtUtil;
import com.tietoevry.surest.member.management.config.PasswordConfig;
import com.tietoevry.surest.member.management.dto.AuthResponse;
import com.tietoevry.surest.member.management.dto.LoginRequest;
import com.tietoevry.surest.member.management.entity.AppUser;
import com.tietoevry.surest.member.management.entity.Role;
import com.tietoevry.surest.member.management.repository.RoleRepository;
import com.tietoevry.surest.member.management.repository.UserRepository;
import com.tietoevry.surest.member.management.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        String token = authService.login(req.username, req.password);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    @Operation(summary = "User Registration")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody LoginRequest request) {

        AuthResponse response = registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    public AuthResponse registerUser(LoginRequest request) {

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordConfig.passwordEncoder().encode(request.getPassword()));
        user.setRoles(Set.of(userRole));


        userRepository.save(user);

        String token = jwtUtil.generateToken(
                user.getUsername(),
                List.of("ROLE_USER")
        );

        return new AuthResponse(token);
    }
}

