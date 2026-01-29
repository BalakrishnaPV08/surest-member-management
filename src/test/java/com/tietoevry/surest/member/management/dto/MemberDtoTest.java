package com.tietoevry.surest.member.management.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberDtoTest {

    @Test
    void noArgsConstructor_shouldCreateEmptyObject() {
        MemberDto dto = new MemberDto();

        assertNull(dto.getId());
        assertNull(dto.getFirstName());
        assertNull(dto.getLastName());
        assertNull(dto.getDateOfBirth());
        assertNull(dto.getEmail());
    }

    @Test
    void allArgsConstructor_shouldSetAllFields() {
        UUID id = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1990, 5, 20);

        MemberDto dto = new MemberDto(
                id,
                "John",
                "Doe",
                dob,
                "john.doe@test.com"
        );

        assertEquals(id, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals(dob, dto.getDateOfBirth());
        assertEquals("john.doe@test.com", dto.getEmail());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        MemberDto dto = new MemberDto();

        UUID id = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1985, 3, 15);

        dto.setId(id);
        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setDateOfBirth(dob);
        dto.setEmail("alice.smith@test.com");

        assertEquals(id, dto.getId());
        assertEquals("Alice", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals(dob, dto.getDateOfBirth());
        assertEquals("alice.smith@test.com", dto.getEmail());
    }

    @Test
    void equalsAndHashCode_shouldBeBasedOnFields() {
        UUID id = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1992, 8, 10);

        MemberDto dto1 = new MemberDto(
                id, "Bob", "Brown", dob, "bob@test.com"
        );

        MemberDto dto2 = new MemberDto(
                id, "Bob", "Brown", dob, "bob@test.com"
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void toString_shouldContainImportantFields() {
        MemberDto dto = new MemberDto(
                UUID.randomUUID(),
                "Jane",
                "Doe",
                LocalDate.of(1999, 12, 1),
                "jane.doe@test.com"
        );

        String toString = dto.toString();

        assertTrue(toString.contains("Jane"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("jane.doe@test.com"));
    }
}
