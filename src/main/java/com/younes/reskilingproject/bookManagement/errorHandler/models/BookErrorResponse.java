package com.younes.reskilingproject.bookManagement.errorHandler.models;

import com.younes.reskilingproject.bookManagement.errorHandler.GlobalNotFoundException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class BookErrorResponse extends GlobalNotFoundException {
    public BookErrorResponse() {
    }

    public BookErrorResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        super(status, message, timestamp);
    }
}
