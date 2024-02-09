package com.younes.reskilingproject.bookManagement.errorHandler;

public class CategoryException extends RuntimeException{
    public CategoryException(String message) {
        super(message);
    }
    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }
    public CategoryException(Throwable cause) {
        super(cause);
    }
}
