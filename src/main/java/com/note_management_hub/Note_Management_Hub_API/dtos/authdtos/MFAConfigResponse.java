package com.note_management_hub.Note_Management_Hub_API.dtos.authdtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MFAConfigResponse {
    private String secret;

    // The standard 'otpauth://' URI that encodes the secret, issuer, and username
    // This is what the Frontend will turn into a QR Code
    private String qrCodeUri;
    private List<String> recoveryCode;
}
