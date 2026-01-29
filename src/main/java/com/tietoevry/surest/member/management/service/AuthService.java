package com.tietoevry.surest.member.management.service;

import com.tietoevry.surest.member.management.config.JwtUtil;
import com.tietoevry.surest.member.management.entity.AppUser;
import com.tietoevry.surest.member.management.entity.Role;
import com.tietoevry.surest.member.management.exception.ApiException;
import com.tietoevry.surest.member.management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException.NotFound("user", "not-found"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new ApiException.Unauthorized("user", "invalid-credentials");
        }

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return jwtUtil.generateToken(user.getUsername(), roles);
    }
}

