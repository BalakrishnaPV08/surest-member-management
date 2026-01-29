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
        assertNotNull(user.getRoles()); // ✅ roles is initialized
        assertTrue(user.getRoles().isEmpty());
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
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
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
    void roles_shouldAllowMultipleRoles() {
        AppUser user = new AppUser();

        Role admin = new Role();
        admin.setName("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        user.setRoles(Set.of(admin, userRole));

        assertEquals(2, user.getRoles().size());
    }
}
