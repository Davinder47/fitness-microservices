package com.fitness.userservice.service;

import com.fitness.userservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // 1. Secret Key: In production/Render, this should be an Environment Variable
    // This string must be at least 32 characters long for HS256
    private static final String SECRET = "your_fitness_app_super_secret_key_1234567890_abcdefg";

    // 2. Generate the Token
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        // We embed the Role and UserID so the Gateway doesn't have to call the DB again
        claims.put("role", user.getRole().name());
        claims.put("userId", user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Token valid for 24 hours
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 3. Helper to decode the secret key
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}