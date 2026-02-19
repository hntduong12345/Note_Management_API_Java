package com.note_management_hub.Note_Management_Hub_API.services.interfaces;

import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.AuthResponse;
import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.LoginRequest;
import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.MFAConfigResponse;
import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.RegisterRequest;
import com.note_management_hub.Note_Management_Hub_API.dtos.userdtos.UserResponse;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    public CompletableFuture<AuthResponse> register(RegisterRequest request);
    public CompletableFuture<AuthResponse> login(LoginRequest request);
    public CompletableFuture<UserResponse> getProfile(UUID id);
//    public CompletableFuture<MFAConfigResponse> setupMfaCode(UUID userId);
//    public CompletableFuture<Boolean> verifyMfaCode(UUID userId, int code);
}
