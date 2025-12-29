package com.tietoevry.surest.member.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    public UUID id;
    public String firstName;
    public String lastName;
    public LocalDate dateOfBirth;
    public String email;
}

