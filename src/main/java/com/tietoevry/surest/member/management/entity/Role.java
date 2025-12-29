package com.tietoevry.surest.member.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }

//    public UUID getId() { return id; }
//    public void setId(UUID id) { this.id = id; }
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
}
