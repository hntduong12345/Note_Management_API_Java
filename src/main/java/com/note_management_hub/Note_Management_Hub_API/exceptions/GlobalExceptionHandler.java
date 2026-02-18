package com.note_management_hub.Note_Management_Hub_API.exceptions;

import com.note_management_hub.Note_Management_Hub_API.exceptions.exceptionCases.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = (BadRequestException.class))
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception){
        ExceptionObject badRequestException = new ExceptionObject(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                exception.getError(),
                exception.getMessage(),
                exception.getCode(),
                exception.getCause()
        );

        return new ResponseEntity<>(badRequestException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = (ConflictException.class))
    public ResponseEntity<Object> handleConflictException(ConflictException exception){
        ExceptionObject conflictException = new ExceptionObject(
                Instant.now(),
                HttpStatus.CONFLICT,
                exception.getError(),
                exception.getMessage(),
                exception.getCode(),
                exception.getCause()
        );

        return new ResponseEntity<>(conflictException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = (InternalException.class))
    public ResponseEntity<Object> handleInternalException(InternalException exception){
        ExceptionObject internalException = new ExceptionObject(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getError(),
                exception.getMessage(),
                exception.getCode(),
                exception.getCause()
        );

        return new ResponseEntity<>(internalException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = (NotFoundException.class))
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception){
        ExceptionObject notFoundException = new ExceptionObject(
                Instant.now(),
                HttpStatus.NOT_FOUND,
                exception.getError(),
                exception.getMessage(),
                exception.getCode(),
                exception.getCause()
        );

        return new ResponseEntity<>(notFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = (UnauthorizedException.class))
    public ResponseEntity<Object> handleUnauthorizeException(UnauthorizedException exception){
        ExceptionObject unauthorizedException = new ExceptionObject(
                Instant.now(),
                HttpStatus.UNAUTHORIZED,
                exception.getError(),
                exception.getMessage(),
                exception.getCode(),
                exception.getCause()
        );

        return new ResponseEntity<>(unauthorizedException, HttpStatus.UNAUTHORIZED);
    }
}
