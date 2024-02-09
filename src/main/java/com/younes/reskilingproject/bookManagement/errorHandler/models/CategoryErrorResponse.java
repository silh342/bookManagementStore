package com.younes.reskilingproject.bookManagement.errorHandler.models;

import com.younes.reskilingproject.bookManagement.errorHandler.GlobalNotFoundException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CategoryErrorResponse extends GlobalNotFoundException {
    public CategoryErrorResponse() {
    }
    public CategoryErrorResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        super(status, message, timestamp);
    }
}
