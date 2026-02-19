package com.note_management_hub.Note_Management_Hub_API.services.impls;

import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.AuthResponse;
import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.LoginRequest;
import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.MFAConfigResponse;
import com.note_management_hub.Note_Management_Hub_API.dtos.authdtos.RegisterRequest;
import com.note_management_hub.Note_Management_Hub_API.dtos.userdtos.UserResponse;
import com.note_management_hub.Note_Management_Hub_API.exceptions.exceptionCases.BadRequestException;
import com.note_management_hub.Note_Management_Hub_API.exceptions.exceptionCases.NotFoundException;
import com.note_management_hub.Note_Management_Hub_API.models.User;
import com.note_management_hub.Note_Management_Hub_API.repositories.UserRepository;
import com.note_management_hub.Note_Management_Hub_API.securities.jwt.JwtService;
import com.note_management_hub.Note_Management_Hub_API.services.interfaces.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final GoogleAuthenticator gAuth;

    @Override
    @Async("taskExecutor")
    @Transactional
    public CompletableFuture<AuthResponse> register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email has already registered", "Bad Request", "ERROR_ASYNC_EMAILEXISTED");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setEmail(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(newUser);

        //Generate JWT token and response
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_FULL")
        );

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                savedUser.getEmail(),
                savedUser.getPassword(),
                authorities
        );

        String jwtToken = jwtService.generateToken(userDetails);

        return CompletableFuture.completedFuture(
                AuthResponse.builder()
                        .status("SUCCESS")
                        .token(jwtToken)
                        .userId(savedUser.getId())
                        .email(savedUser.getEmail())
                        .build()
        );
    }

    @Override
    @Async("taskExecutor")
    @Transactional
    public CompletableFuture<AuthResponse> login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User is not found", "Not Found", "ERR_ASYNC_OBJECTNOTFOUND"));

//        if(user.getMfaSecret() != null && !user.getMfaSecret().isEmpty()){
//            String mfaToken = jwtService.generateMfaToken(userDetails);
//            return CompletableFuture.completedFuture(
//                    AuthResponse.builder()
//                            .status("MFA_REQUIRED")
//                            .mfaToken(mfaToken)
//                            .userId(user.getId())
//                            .email(user.getEmail())
//                            .build()
//            );
//        }

        String jwtToken = jwtService.generateToken(userDetails);
        return CompletableFuture.completedFuture(
                AuthResponse.builder()
                        .status("SUCCESS")
                        .token(jwtToken)
                        .userId(user.getId())
                        .email(user.getEmail())
                        .build()
        );
    }

    @Override
    @Async("taskExecutor")
    @Transactional(readOnly = true)
    public CompletableFuture<UserResponse> getProfile(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User is not found", "Not Found", "ERR_ASYNC_OBJECTNOTFOUND"));
        UserResponse response = new UserResponse(
                user.getId(),
                user.getEmail()
        );

        return CompletableFuture.completedFuture(response);
    }

//    @Override
//    @Async("taskExecutor")
//    @Transactional
//    public CompletableFuture<MFAConfigResponse> setupMfaCode(UUID userId) {
//        //Get User object
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User is not found", "Not Found", "ERR_ASYNC_OBJECTNOTFOUND"));
//
//        //Generate the shared secret
//        final GoogleAuthenticatorKey key = gAuth.createCredentials();
//        String secret = key.getKey();
//
//        //Save to DB
//        user.setMfaSecret(secret);
//        userRepository.save(user);
//
//        //Generate recovery codes
//        List<String> recoveryCodes = generateRecoveryCodes(8);
//
//        //Generate the OTPAuth URI
//        String qrCodeUri = GoogleAuthenticatorQRGenerator.getOtpAuthURL("NoteManagementHub", "email", key);
//
//        var response = new MFAConfigResponse(secret, qrCodeUri, recoveryCodes);
//        return CompletableFuture.completedFuture(response);
//    }
//
//    private List<String> generateRecoveryCodes(int count){
//        List<String> codes = new ArrayList<>();
//        SecureRandom random = new SecureRandom();
//
//        for(int i=0; i<count; i++){
//            String code = random.ints(48, 91)
//                    .filter(c -> (c <= 57 || c >= 65))
//                    .limit(10)
//                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                    .toString();
//
//            codes.add(code);
//        }
//
//        return codes;
//    }
//
//    @Override
//    @Async("taskExecutor")
//    @Transactional
//    public CompletableFuture<Boolean> verifyMfaCode(UUID userId, int code) {
//        //Get User object
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User is not found", "Not Found", "ERR_ASYNC_OBJECTNOTFOUND"));
//
//        boolean isValid = gAuth.authorize(user.getMfaSecret(), code);
//
//        return CompletableFuture.completedFuture(isValid);
//    }
}
