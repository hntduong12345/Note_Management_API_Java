package com.note_management_hub.Note_Management_Hub_API.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionObject {
    private Instant timestamp;
    private final HttpStatus httpStatus;
    private String error;
    private final String message;
    private final String code;
    private final Throwable throwable;
}
