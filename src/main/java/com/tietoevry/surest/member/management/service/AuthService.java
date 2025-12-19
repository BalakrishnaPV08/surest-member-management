package com.tietoevry.surest.member.management.service;

import com.tietoevry.surest.member.management.config.JwtUtil;
import com.tietoevry.surest.member.management.entity.AppUser;
import com.tietoevry.surest.member.management.exception.ApiException;
import com.tietoevry.surest.member.management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new ApiException.NotFound("User not found"));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) throw new ApiException.Unauthorized("Invalid credentials");
        return jwtUtil.generateToken(user.getUsername(), user.getRole().getName());
    }
}

