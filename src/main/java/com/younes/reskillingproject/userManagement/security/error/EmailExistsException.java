package com.younes.reskillingproject.userManagement.security.error;

public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String message) {
        super(message);
    }
}
