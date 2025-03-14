package com.demo.taskapprovalsystem.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // Secret key for signing JWT token, usually stored in application.properties or environment variables
    @Value("${jwt.secret}")
    private String secretKey;

    // JWT expiration time (e.g., 1 hour)
    @Value("${jwt.expiration}")
    private long expirationTime;

    // Method to generate JWT token
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(username) // Set the subject to be the username (email)
                .setIssuedAt(now)     // Set the issue date of the token
                .setExpiration(expiryDate)  // Set the expiration date of the token
                .signWith(SignatureAlgorithm.HS512, secretKey)  // Sign with the secret key
                .compact();
    }

    // Method to validate and parse the JWT token
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Method to get the username (subject) from the JWT token
    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    // Method to check if the token is expired
    public boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    // Method to validate the JWT token
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

