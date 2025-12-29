package com.tietoevry.surest.member.management.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void constructorShouldSetToken() {
        String token = "jwt-token-123";

        AuthResponse response = new AuthResponse(token);

        assertEquals(token, response.getToken());
    }

    @Test
    void setterShouldUpdateToken() {
        AuthResponse response = new AuthResponse("old-token");

        response.setToken("new-token");

        assertEquals("new-token", response.getToken());
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        AuthResponse r1 = new AuthResponse("token");
        AuthResponse r2 = new AuthResponse("token");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void toStringShouldContainToken() {
        AuthResponse response = new AuthResponse("jwt-token");

        assertTrue(response.toString().contains("jwt-token"));
    }
}

