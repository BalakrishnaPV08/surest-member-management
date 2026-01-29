package com.tietoevry.surest.member.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMemberRequest {
    @NotBlank public String firstName;
    @NotBlank public String lastName;
    @NotNull public LocalDate dateOfBirth;
    @Email @NotBlank public String email;
}

