package com.note_management_hub.Note_Management_Hub_API.dtos.authdtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthResponse{
    private final String status; // SUCCESS, MFA_REQUIRED, LOCKED
    private final String token; // Jwt token
    private final String mfaToken;
    private final UUID userId;
    private final String email;
}
