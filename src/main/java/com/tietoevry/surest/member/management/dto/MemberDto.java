package com.tietoevry.surest.member.management.dto;

import java.time.LocalDate;
import java.util.UUID;


public class MemberDto {
    public UUID id;
    public String firstName;
    public String lastName;
    public LocalDate dateOfBirth;
    public String email;
}

