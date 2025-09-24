package com.foodhub.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.util.Date;

public class JwtUtils {

    private static final String JWT_SECRET = "your_secret_key";  // Use a secure secret in real apps
    private static final long JWT_EXPIRATION_MS = 86400000; // 1 day

    public static String generateToken(Object object) {
        return Jwts.builder()
            .setSubject(object.toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
    }

    public static Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(token)
            .getBody();

        return Long.parseLong(claims.getSubject());
    }

    // Add token validation method if needed
}
