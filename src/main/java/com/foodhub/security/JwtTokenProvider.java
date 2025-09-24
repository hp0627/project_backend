package com.foodhub.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import com.foodhub.model.User;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET = "your_jwt_secret_key_here"; // Use a strong secret and store safely
    private final long JWT_EXPIRATION = 86400000; // 1 day in milliseconds

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .claim("email", user.getEmail())
                .claim("full_name", user.getFullName())
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // log error
        }
        return false;
    }
}
