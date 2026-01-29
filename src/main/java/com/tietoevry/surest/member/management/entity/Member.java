package com.tietoevry.surest.member.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() { updatedAt = Instant.now(); }
}