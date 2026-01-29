package com.tietoevry.surest.member.management.config;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    @Test
    void generateAndParseToken() {
        JwtUtil util = new JwtUtil("test-secret-key-12345678901234567890", 3600000);

        String token = util.generateToken("balu", Collections.singletonList("ROLE_ADMIN"));

        Claims claims = util.parse(token).getBody();

        assertEquals("balu", claims.getSubject());
    }
}

