package com.rumor.yumback.utils;

import com.rumor.yumback.enumeration.UserRole;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    private final SecretKey secretKey;

    public JwtUtils(@Value("${jwt.secretKey}") String secretKey) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String generateToken(String username, UserRole role, String picture, Long expiredAt) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .claim("picture", picture)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredAt))
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public String getPicture(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload().get("picture", String.class);
    }
}
