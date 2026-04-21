package com.example.nmcnpm.module.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Tách PasswordEncoder ra file riêng để tránh vòng lặp phụ thuộc (Circular Dependency).
 * Vòng lặp cũ: SecurityConfig -> JwtAuthFilter -> UserServiceImpl -> SecurityConfig.
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
