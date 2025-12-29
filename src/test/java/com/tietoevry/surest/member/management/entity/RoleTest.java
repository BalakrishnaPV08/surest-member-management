package com.tietoevry.surest.member.management.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void noArgsConstructor_shouldCreateEmptyObject() {
        Role role = new Role();

        assertNull(role.getId());
        assertNull(role.getName());
    }

    @Test
    void allArgsConstructor_shouldSetAllFields() {
        UUID id = UUID.randomUUID();

        Role role = new Role(id, "ROLE_ADMIN");

        assertEquals(id, role.getId());
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        Role role = new Role();

        UUID id = UUID.randomUUID();
        role.setId(id);
        role.setName("ROLE_USER");

        assertEquals(id, role.getId());
        assertEquals("ROLE_USER", role.getName());
    }

    @Test
    void prePersist_shouldGenerateIdWhenIdIsNull() {
        Role role = new Role();

        assertNull(role.getId());

        role.prePersist();

        assertNotNull(role.getId());
    }

    @Test
    void prePersist_shouldNotOverrideExistingId() {
        UUID existingId = UUID.randomUUID();
        Role role = new Role();
        role.setId(existingId);

        role.prePersist();

        assertEquals(existingId, role.getId());
    }

    @Test
    void equalsAndHashCode_shouldWorkForSameData() {
        UUID id = UUID.randomUUID();

        Role r1 = new Role(id, "ROLE_ADMIN");
        Role r2 = new Role(id, "ROLE_ADMIN");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void toString_shouldContainRoleName() {
        Role role = new Role();
        role.setName("ROLE_MANAGER");

        String toString = role.toString();

        assertTrue(toString.contains("ROLE_MANAGER"));
    }
}
