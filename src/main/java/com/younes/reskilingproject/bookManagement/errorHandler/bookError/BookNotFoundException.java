package com.younes.reskilingproject.bookManagement.errorHandler.bookError;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String message) {
        super(message);
    }
    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public BookNotFoundException(Throwable cause) {
        super(cause);
    }
}
