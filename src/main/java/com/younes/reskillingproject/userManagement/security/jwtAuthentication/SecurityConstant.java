package com.younes.reskillingproject.userManagement.security.jwtAuthentication;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstant {
    public static final long JWT_EXPIRATION = 600000;
    public static final SecretKey JWT_SECRET = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
}
