package com.tietoevry.surest.member.management.service;

import com.tietoevry.surest.member.management.entity.AppUser;
import com.tietoevry.surest.member.management.entity.Role;
import com.tietoevry.surest.member.management.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .toArray(String[]::new)
                )
                .build();
    }
}
