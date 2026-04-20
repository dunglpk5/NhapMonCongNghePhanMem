package com.example.nmcnpm.module.core.config;

import com.example.nmcnpm.module.core.exception.AuthException;
import com.example.nmcnpm.module.security.service.ISecurityService;
import com.example.nmcnpm.module.user.service.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthFilter – filter chạy mỗi request (OncePerRequestFilter).
 * Trích xuất JWT từ header Authorization, validate, nạp SecurityContext.
 * Bỏ qua các public endpoint (/auth/login, /auth/logout).
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final ISecurityService securityService;
    private final IUserService     userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest  request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain         filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Không có header hoặc không phải Bearer → bỏ qua
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        // Token không hợp lệ (chữ ký sai, expired)
        if (!securityService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Chưa có authentication trong context → nạp vào
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = securityService.extractUsername(token);

            userService.findByIdentifier(username).ifPresent(user -> {
                if (user.isEnabled()) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, user.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            });
        }

        filterChain.doFilter(request, response);
    }
}
