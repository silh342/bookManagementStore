package com.younes.reskilingproject.bookManagement.errorHandler;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public abstract class GlobalNotFoundException {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;

    public GlobalNotFoundException () {}
    public GlobalNotFoundException(HttpStatus status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
