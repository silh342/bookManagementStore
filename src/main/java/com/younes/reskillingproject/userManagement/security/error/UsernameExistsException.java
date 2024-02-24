package com.younes.reskillingproject.userManagement.security.error;

public class UsernameExistsException extends RuntimeException{

    public UsernameExistsException(String message) {
        super(message);
    }
}
