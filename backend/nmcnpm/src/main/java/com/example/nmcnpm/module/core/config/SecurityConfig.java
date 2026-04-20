package com.example.nmcnpm.module.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.nmcnpm.module.core.response.ApiResponse;
import com.example.nmcnpm.module.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * SecurityConfig – cấu hình Spring Security cho toàn hệ thống.
 *
 * Phân quyền theo vai trò (NFR-09 – RBAC):
 *   ADMIN        → /dashboard/admin/**
 *   TEACHER      → /dashboard/teacher/**
 *   OFFICE_STAFF → /dashboard/office/**
 *   PRINCIPAL    → /dashboard/principal/**
 *   STUDENT      → /dashboard/student/**
 *
 * Stateless: dùng JWT, không dùng HttpSession.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity          // cho phép @PreAuthorize trên method
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter    jwtAuthFilter;
    private final UserServiceImpl  userDetailsService;
    private final ObjectMapper     objectMapper;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    // ─── SecurityFilterChain ──────────────────────────────────────────────────

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Tắt CSRF (dùng JWT, stateless)
            .csrf(AbstractHttpConfigurer::disable)

            // CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Stateless session (JWT)
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Phân quyền endpoint (NFR-09 – RBAC)
            .authorizeHttpRequests(auth -> auth

                // Public endpoints
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/forgot-password").permitAll()

                // Dashboard theo vai trò (FR-07)
                .requestMatchers("/dashboard/admin/**")
                        .hasRole("ADMIN")
                .requestMatchers("/dashboard/teacher/**")
                        .hasRole("TEACHER")
                .requestMatchers("/dashboard/office/**")
                        .hasRole("OFFICE_STAFF")
                .requestMatchers("/dashboard/principal/**")
                        .hasRole("PRINCIPAL")
                .requestMatchers("/dashboard/student/**")
                        .hasRole("STUDENT")

                // API nghiệp vụ theo role
                .requestMatchers("/students/**", "/classes/**", "/subjects/**",
                                  "/timetable/**", "/assignments/**")
                        .hasAnyRole("ADMIN", "OFFICE_STAFF", "PRINCIPAL")
                .requestMatchers("/scores/**", "/conduct/**")
                        .hasAnyRole("ADMIN", "TEACHER", "OFFICE_STAFF")
                .requestMatchers("/reports/**")
                        .hasAnyRole("ADMIN", "PRINCIPAL", "OFFICE_STAFF")
                .requestMatchers("/users/**")
                        .hasRole("ADMIN")

                // Tất cả còn lại cần xác thực
                .anyRequest().authenticated()
            )

            // Xử lý 401 / 403 trả JSON thay vì redirect
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
                        response.getWriter().write(
                                objectMapper.writeValueAsString(
                                        ApiResponse.fail("Vui lòng đăng nhập để tiếp tục!")));
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
                        response.getWriter().write(
                                objectMapper.writeValueAsString(
                                        ApiResponse.fail("Bạn không có quyền truy cập chức năng này!")));
                    })
            )

            // Auth provider (bcrypt)
            .authenticationProvider(authenticationProvider())

            // JWT filter chạy trước UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ─── Beans ───────────────────────────────────────────────────────────────

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * BCryptPasswordEncoder – strength 12 (NFR-10).
     * Khai báo tại đây để tránh circular dependency với SecurityServiceImpl.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
