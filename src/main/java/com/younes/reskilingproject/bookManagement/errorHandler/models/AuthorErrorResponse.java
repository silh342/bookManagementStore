package com.younes.reskilingproject.bookManagement.errorHandler.models;

import com.younes.reskilingproject.bookManagement.errorHandler.GlobalNotFoundException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class AuthorErrorResponse extends GlobalNotFoundException {
    public AuthorErrorResponse() {
    }

    public AuthorErrorResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        super(status, message, timestamp);
    }
}
