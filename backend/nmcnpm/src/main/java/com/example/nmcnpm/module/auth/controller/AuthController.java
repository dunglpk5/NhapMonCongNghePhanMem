package com.example.nmcnpm.module.auth.controller;

import com.example.nmcnpm.module.auth.dto.LoginRequest;
import com.example.nmcnpm.module.auth.dto.LoginResponse;
import com.example.nmcnpm.module.auth.service.IAuthService;
import com.example.nmcnpm.module.core.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController – REST controller cho Auth Module.
 * Tương ứng với AuthController.dangNhapRQ() trong hồ sơ thiết kế.
 *
 * Base path: /api/auth
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    /**
     * POST /api/auth/login
     *
     * dangNhapRQ – nhận request, gọi AuthService.authenticate().
     * Validation @Valid bắt trường rỗng trước khi vào service.
     * GlobalExceptionHandler xử lý tất cả AuthException.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        String ip = extractClientIp(httpRequest);
        LoginResponse response = authService.authenticate(request, ip);

        return ResponseEntity.ok(
                ApiResponse.ok("Đăng nhập thành công!", response));
    }

    /**
     * POST /api/auth/logout
     * Yêu cầu header: Authorization: Bearer <token>
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            HttpServletRequest httpRequest) {

        String token = extractBearerToken(httpRequest);
        String ip    = extractClientIp(httpRequest);
        authService.logout(token, ip);

        return ResponseEntity.ok(ApiResponse.ok("Đăng xuất thành công!"));
    }

    /**
     * GET /api/auth/me
     * Trả về thông tin user đang đăng nhập (dùng cho frontend kiểm tra session).
     * Yêu cầu JWT hợp lệ qua JwtAuthFilter.
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDetails>> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(ApiResponse.ok("OK", userDetails));
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    /** Lấy IP thực khi đằng sau reverse proxy (nginx, load balancer). */
    private String extractClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
