package com.younes.reskilingproject.bookManagement.errorHandler;

public class AuthorException extends RuntimeException {
    public AuthorException(String message) {
        super(message);
    }
    public AuthorException(String message, Throwable cause) {
        super(message, cause);
    }
    public AuthorException(Throwable cause) {
        super(cause);
    }
}
