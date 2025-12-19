package com.tietoevry.surest.member.management.service;

import com.tietoevry.surest.member.management.entity.AppUser;
import com.tietoevry.surest.member.management.entity.Role;
import com.tietoevry.surest.member.management.repository.RoleRepository;
import com.tietoevry.surest.member.management.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository; this.userRepository = userRepository; this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Role admin = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role r = new Role(); r.setName("ROLE_ADMIN"); return roleRepository.save(r);
        });
        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role r = new Role(); r.setName("ROLE_USER"); return roleRepository.save(r);
        });

        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser u = new AppUser(); u.setUsername("admin"); u.setPasswordHash(passwordEncoder.encode("adminpass")); u.setRole(admin); userRepository.save(u);
        }
        if (userRepository.findByUsername("user").isEmpty()) {
            AppUser u = new AppUser(); u.setUsername("user"); u.setPasswordHash(passwordEncoder.encode("userpass")); u.setRole(userRole); userRepository.save(u);
        }
    }
}

