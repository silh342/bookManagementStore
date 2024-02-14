package com.younes.reskillingproject.userManagement.security;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstant {
    public static final long JWT_EXPIRATION = 7200;
    public static final SecretKey JWT_SECRET = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
}
