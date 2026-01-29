package com.tietoevry.surest.member.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank public String username;
    @NotBlank public String password;
}

