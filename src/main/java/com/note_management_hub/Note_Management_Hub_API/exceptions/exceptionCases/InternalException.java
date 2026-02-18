package com.note_management_hub.Note_Management_Hub_API.exceptions.exceptionCases;

import lombok.Getter;

@Getter
public class InternalException extends RuntimeException {
    private final String error;
    private final String code;

    public InternalException(String message, String error, String code) {
        super(message);
        this.error = error;
        this.code = code;
    }

    public InternalException(String message, Throwable cause, String error, String code) {
        super(message, cause);
        this.error = error;
        this.code = code;
    }
}
