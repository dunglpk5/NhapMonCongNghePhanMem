package com.example.nmcnpm.module.security.service;

import com.example.nmcnpm.module.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * SecurityServiceImpl – xử lý bcrypt và JWT.
 * Module: Security Module.
 */
@Service
@Slf4j
public class SecurityServiceImpl implements ISecurityService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final SecretKey              jwtSecretKey;
    private final long                   jwtExpirationMs;

    public SecurityServiceImpl(
            @Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.jwt.expiration-ms}") long jwtExpirationMs) {
        // BCrypt strength=12 – cân bằng bảo mật và hiệu năng
        this.passwordEncoder  = new BCryptPasswordEncoder(12);
        this.jwtSecretKey     = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationMs  = jwtExpirationMs;
    }

    /** verifyPassword – so sánh raw vs bcrypt hash (NFR-10). */
    @Override
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    /**
     * generateJwt – tạo JWT chứa userId, username, role.
     * Payload đủ để AuthFilter xác thực mà không cần query DB mỗi request.
     */
    @Override
    public String generateJwt(User user) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getUserId())
                .claim("role",   user.getRole().name())
                .claim("email",  user.getEmail())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(jwtSecretKey)
                .compact();
    }

    @Override
    public Integer extractUserId(String token) {
        return getClaims(token).get("userId", Integer.class);
    }

    @Override
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // ─── Private helper ───────────────────────────────────────────────────

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
