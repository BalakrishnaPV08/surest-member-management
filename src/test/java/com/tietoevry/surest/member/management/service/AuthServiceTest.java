package com.tietoevry.surest.member.management.service;

import com.tietoevry.surest.member.management.config.JwtUtil;
import com.tietoevry.surest.member.management.entity.AppUser;
import com.tietoevry.surest.member.management.entity.Role;
import com.tietoevry.surest.member.management.exception.ApiException;
import com.tietoevry.surest.member.management.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private final UserRepository repo = mock(UserRepository.class);
    private final PasswordEncoder encoder = mock(PasswordEncoder.class);
    private final JwtUtil jwt = mock(JwtUtil.class);

    private final AuthService service = new AuthService(repo, encoder, jwt);

    @Test
    void login_success_returnsToken() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        AppUser user = new AppUser();
        user.setUsername("balu");
        user.setPasswordHash("balu123");
        user.setRoles(Set.of(role));

        when(repo.findByUsername("balu")).thenReturn(Optional.of(user));
        when(encoder.matches("balu123", "balu123")).thenReturn(true);
        when(jwt.generateToken("balu", Collections.singletonList("ROLE_ADMIN"))).thenReturn("TOKEN123");

        String token = service.login("balu", "balu123");

        assertEquals("TOKEN123", token);
    }

    @Test
    void login_wrongPassword_throwsUnauthorized() {
        AppUser user = new AppUser();
        user.setUsername("balu");
        user.setPasswordHash("correct");

        when(repo.findByUsername("balu")).thenReturn(Optional.of(user));
        when(encoder.matches("wrong", "correct")).thenReturn(false);

        assertThrows(ApiException.Unauthorized.class, () ->
                service.login("balu", "wrong"));
    }

    @Test
    void login_userNotFound_throwsNotFound() {
        when(repo.findByUsername("x")).thenReturn(Optional.empty());

        assertThrows(ApiException.NotFound.class,
                () -> service.login("x", "123"));
    }

}
