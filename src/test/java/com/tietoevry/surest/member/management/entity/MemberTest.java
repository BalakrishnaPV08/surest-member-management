package com.tietoevry.surest.member.management.entity;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void noArgsConstructor_shouldCreateEmptyObject() {
        Member member = new Member();

        assertNull(member.getId());
        assertNull(member.getFirstName());
        assertNull(member.getLastName());
        assertNull(member.getDateOfBirth());
        assertNull(member.getEmail());
        assertNull(member.getCreatedAt());
        assertNull(member.getUpdatedAt());
    }

    @Test
    void allArgsConstructor_shouldSetAllFields() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        LocalDate dob = LocalDate.of(1990, 6, 15);

        Member member = new Member(
                id,
                "John",
                "Doe",
                dob,
                "john.doe@test.com",
                now,
                now
        );

        assertEquals(id, member.getId());
        assertEquals("John", member.getFirstName());
        assertEquals("Doe", member.getLastName());
        assertEquals(dob, member.getDateOfBirth());
        assertEquals("john.doe@test.com", member.getEmail());
        assertEquals(now, member.getCreatedAt());
        assertEquals(now, member.getUpdatedAt());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        Member member = new Member();

        UUID id = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1985, 4, 20);
        Instant now = Instant.now();

        member.setId(id);
        member.setFirstName("Alice");
        member.setLastName("Smith");
        member.setDateOfBirth(dob);
        member.setEmail("alice.smith@test.com");
        member.setCreatedAt(now);
        member.setUpdatedAt(now);

        assertEquals(id, member.getId());
        assertEquals("Alice", member.getFirstName());
        assertEquals("Smith", member.getLastName());
        assertEquals(dob, member.getDateOfBirth());
        assertEquals("alice.smith@test.com", member.getEmail());
        assertEquals(now, member.getCreatedAt());
        assertEquals(now, member.getUpdatedAt());
    }

    @Test
    void prePersist_shouldGenerateIdAndTimestamps() {
        Member member = new Member();

        assertNull(member.getId());
        assertNull(member.getCreatedAt());
        assertNull(member.getUpdatedAt());

        member.prePersist();

        assertNotNull(member.getId());
        assertNotNull(member.getCreatedAt());
        assertNotNull(member.getUpdatedAt());
        assertEquals(member.getCreatedAt(), member.getUpdatedAt());
    }

    @Test
    void prePersist_shouldNotOverrideExistingId() {
        UUID existingId = UUID.randomUUID();
        Member member = new Member();
        member.setId(existingId);

        member.prePersist();

        assertEquals(existingId, member.getId());
    }

    @Test
    void preUpdate_shouldUpdateUpdatedAtOnly() throws InterruptedException {
        Member member = new Member();
        member.prePersist();

        Instant createdAt = member.getCreatedAt();
        Instant oldUpdatedAt = member.getUpdatedAt();

        Thread.sleep(5); // ensure timestamp difference
        member.preUpdate();

        assertEquals(createdAt, member.getCreatedAt());
        assertTrue(member.getUpdatedAt().isAfter(oldUpdatedAt));
    }

    @Test
    void equalsAndHashCode_shouldWorkForSameData() {
        UUID id = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1991, 9, 10);
        Instant now = Instant.now();

        Member m1 = new Member(
                id, "Bob", "Brown", dob, "bob@test.com", now, now
        );

        Member m2 = new Member(
                id, "Bob", "Brown", dob, "bob@test.com", now, now
        );

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void toString_shouldContainImportantFields() {
        Member member = new Member();
        member.setFirstName("Jane");
        member.setLastName("Doe");

        String toString = member.toString();

        assertTrue(toString.contains("Jane"));
        assertTrue(toString.contains("Doe"));
    }
}
