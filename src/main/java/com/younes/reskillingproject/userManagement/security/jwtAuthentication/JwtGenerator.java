package com.younes.reskillingproject.userManagement.security.jwtAuthentication;

import com.younes.reskillingproject.userManagement.security.dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        // i want to send the roles in the jwt Token to handle user interaction in the front end
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
        // and ofc for obvious reasons i want to send the email too
        String email = ((CustomUserDetails) authentication.getPrincipal()).getEmail();


        Date expirationDate = new Date(currentDate.getTime() + SecurityConstant.JWT_EXPIRATION);
        return  Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SecurityConstant.JWT_SECRET)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstant.JWT_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SecurityConstant.JWT_SECRET).build().parseClaimsJws(token);
            return true;
        } catch(Exception exc) {
            throw new AuthenticationCredentialsNotFoundException("Token was expired or incorrect ," + exc.getMessage());
        }
    }
}
