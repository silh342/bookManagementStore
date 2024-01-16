package com.younes.reskilingproject.bookManagement.errorHandler.authorError;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String message) {
        super(message);
    }
    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public AuthorNotFoundException(Throwable cause) {
        super(cause);
    }
}
