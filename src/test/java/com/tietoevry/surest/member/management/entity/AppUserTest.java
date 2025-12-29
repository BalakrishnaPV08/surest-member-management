package com.tietoevry.surest.member.management.entity;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    @Test
    void noArgsConstructor_shouldCreateEmptyObject() {
        AppUser user = new AppUser();

        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPasswordHash());
        assertNull(user.getRoles());
    }

    @Test
    void allArgsConstructor_shouldSetAllFields() {
        UUID id = UUID.randomUUID();
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        AppUser user = new AppUser(
                id,
                "admin",
                "password-hash",
                Set.of(role)
        );

        assertEquals(id, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("password-hash", user.getPasswordHash());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        AppUser user = new AppUser();

        UUID id = UUID.randomUUID();
        Role role = new Role();
        role.setName("ROLE_USER");

        user.setId(id);
        user.setUsername("user");
        user.setPasswordHash("hash");
        user.setRoles(Set.of(role));

        assertEquals(id, user.getId());
        assertEquals("user", user.getUsername());
        assertEquals("hash", user.getPasswordHash());
        assertEquals(Set.of(role), user.getRoles());
    }

    @Test
    void prePersist_shouldGenerateUuidWhenIdIsNull() {
        AppUser user = new AppUser();

        assertNull(user.getId());

        user.prePersist();

        assertNotNull(user.getId());
    }

    @Test
    void prePersist_shouldNotOverrideExistingId() {
        UUID existingId = UUID.randomUUID();
        AppUser user = new AppUser();
        user.setId(existingId);

        user.prePersist();

        assertEquals(existingId, user.getId());
    }

    @Test
    void equalsAndHashCode_shouldWorkForSameData() {
        UUID id = UUID.randomUUID();

        AppUser u1 = new AppUser(
                id,
                "sameUser",
                "hash",
                Set.of()
        );

        AppUser u2 = new AppUser(
                id,
                "sameUser",
                "hash",
                Set.of()
        );

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void toString_shouldContainUsername() {
        AppUser user = new AppUser();
        user.setUsername("testUser");

        String toString = user.toString();

        assertTrue(toString.contains("testUser"));
    }
}
