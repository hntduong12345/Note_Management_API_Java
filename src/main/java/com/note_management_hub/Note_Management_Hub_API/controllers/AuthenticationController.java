package com.note_management_hub.Note_Management_Hub_API.controllers;

import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.AuthResponse;
import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.LoginRequest;
import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.RegisterRequest;
import com.note_management_hub.Note_Management_Hub_API.exceptions.exceptionCases.InternalException;
import com.note_management_hub.Note_Management_Hub_API.services.impls.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController extends BaseController{
    private final UserServiceImpl userService;

    @Operation(summary = "Login", description = "Login. Accessible Role: All")
    @ApiResponse(
            responseCode = "200",
            description = "Login successfully",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "Login Failed: Invalid email or password."
    )
    @PostMapping("login")
    public CompletableFuture<AuthResponse> login(@RequestBody LoginRequest request){
        logger.info("Controller: login");
        return userService.login(request)
                .exceptionally(ex ->{
                    logger.error("Error when logging into a system");
                    throw new InternalException("Error when logging into a system", ex.getCause(), "Internal Error", "ERROR_ASYNC_LOGINERROR");
                });
    }

    @Operation(summary = "Register", description = "Register. Accessible Role: All")
    @ApiResponse(
            responseCode = "200",
            description = "Register successfully",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthResponse.class)
            )
    )
    @PostMapping("register")
    public CompletableFuture<AuthResponse> register(@RequestBody RegisterRequest request){
        logger.info("Controller: register");
        return userService.register(request)
                .exceptionally(ex ->{
                    logger.error("Error when registering into a system");
                    throw new InternalException("Error when logging into a system", ex.getCause(), "Internal Error", "ERROR_ASYNC_LOGINERROR");

                });
    }
}
