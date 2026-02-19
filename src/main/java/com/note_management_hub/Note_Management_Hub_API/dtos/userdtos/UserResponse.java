package com.note_management_hub.Note_Management_Hub_API.dtos.userdtos;

import java.util.UUID;

public record UserResponse(
    UUID id,
    String email
) {}
