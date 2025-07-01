package com.momodev.drivingschool.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long validityMs;

    public JwtTokenProvider(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-ms}") long validityMs) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityMs = validityMs;
    }

    /** Genera un JWT firmado */
    public String generate(String username) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username) // nuevo DSL 0.12.x
                .issuedAt(now)
                .expiration(new Date(now.getTime() + validityMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Devuelve el subject (= username) del token */
    public String getUsername(String token) {
        return Jwts.parser() // ← API 0.12.x
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /** Comprueba si el token es válido y está firmado con nuestra clave */
    public boolean isValid(String token) {
        try {
            getUsername(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}
