package com.example.nmcnpm.module.auth.service;

import com.example.nmcnpm.module.accountpolicy.service.IAccountPolicyService;
import com.example.nmcnpm.module.accountpolicy.service.IAccountPolicyService.LockStatus;
import com.example.nmcnpm.module.auth.dto.LoginRequest;
import com.example.nmcnpm.module.auth.dto.LoginResponse;
import com.example.nmcnpm.module.core.exception.AuthException;
import com.example.nmcnpm.module.logging.service.ILoggingService;
import com.example.nmcnpm.module.security.service.ISecurityService;
import com.example.nmcnpm.module.user.entity.User;
import com.example.nmcnpm.module.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthServiceImpl – triển khai đầy đủ luồng đăng nhập theo hồ sơ thiết kế.
 *
 * Luồng xử lý (theo Process trong hồ sơ):
 *  1. Kiểm tra trạng thái khóa tạm thời
 *  2. Tìm user theo email
 *  3. Kiểm tra tài khoản bị khóa vĩnh viễn
 *  4. So sánh mật khẩu với bcrypt hash
 *  5. Nếu sai → tăng failed_attempts, khóa nếu đủ 5 lần
 *  6. Nếu đúng → reset attempts, tạo JWT, ghi log, trả redirectUrl theo role
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserService          userService;
    private final ISecurityService      securityService;
    private final IAccountPolicyService accountPolicyService;
    private final ILoggingService       loggingService;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    // ─── authenticate ────────────────────────────────────────────────────────

    @Override
    @Transactional
    public LoginResponse authenticate(LoginRequest request, String ipAddress) {

        String email = request.getEmail().trim();
        String rawPassword = request.getPassword();
        

        // ── Bước 1: Tìm user theo email ──────────────────────────────────────
        // Trả cùng một thông báo lỗi cho cả "không tìm thấy" và "sai mật khẩu"
        // để tránh user enumeration attack.
        User user = userService.findByEmail(email)
                .orElseThrow(() -> {
                    loggingService.logLoginEvent(null, false, ipAddress,
                            "email_not_found:" + email);
                    return new AuthException.InvalidCredentialsException();
                });

        // ── Bước 2: Kiểm tra khóa tạm thời (NFR-13) ─────────────────────────
        LockStatus lockStatus = accountPolicyService.checkLockStatus(user.getUserId());
        if (lockStatus.isLocked()) {
            loggingService.logLoginEvent(user.getUserId(), false, ipAddress,
                    "account_temp_locked");
            throw new AuthException.AccountTemporarilyLockedException(
                    lockStatus.remainingSeconds());
        }


        // ── Bước 3: Kiểm tra khóa vĩnh viễn ─────────────────────────────────
        if ("locked".equals(user.getStatus())) {
            loggingService.logLoginEvent(user.getUserId(), false, ipAddress,
                    "account_permanently_locked");
            throw new AuthException.AccountPermanentlyLockedException();
        }

        // ── Bước 4: Xác thực mật khẩu (bcrypt – NFR-10) ─────────────────────
        boolean passwordMatch = securityService.verifyPassword(rawPassword, user.getPasswordHash());
        if (!passwordMatch) {
            // Bước 4a: tăng failed_attempts, tự động khóa nếu đủ 5 lần
            int attempts = accountPolicyService.incrementFailedAttempts(user.getUserId());
            loggingService.logLoginEvent(user.getUserId(), false, ipAddress,
                    "wrong_password:attempt=" + attempts);
            throw new AuthException.InvalidCredentialsException();
        }
        

        // ── Bước 5: Đăng nhập thành công ─────────────────────────────────────
        return handleLoginSuccess(user, ipAddress);
    }

    

    // ─── handleLoginSuccess ───────────────────────────────────────────────────

    /**
     * handleLoginSuccess – reset attempts, tạo JWT, ghi log, trả redirectUrl.
     * Tương ứng với AuthService.handleLoginSuccess() trong hồ sơ thiết kế.
     */
    private LoginResponse handleLoginSuccess(User user, String ipAddress) {

        // Reset failed attempts về 0
        accountPolicyService.resetFailedAttempts(user.getUserId());

        // Tạo JWT token
        String jwt = securityService.generateJwt(user);

        // Ghi audit log bất đồng bộ (NFR-06)
        String meta = String.format("role=%s,username=%s", user.getRole().name(), user.getUsername());
        loggingService.logLoginEvent(user.getUserId(), true, ipAddress, meta);

        log.info("Login success: userId={}, role={}, ip={}",
                user.getUserId(), user.getRole(), ipAddress);

        // Trả về redirectUrl theo vai trò (FR-07)
        return LoginResponse.builder()
                .token(jwt)
                .tokenType("Bearer")
                .redirectUrl(user.getRole().getDashboardUrl())
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .expiresIn(jwtExpirationMs)
                .build();
    }

    // ─── logout ───────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void logout(String token, String ipAddress) {
        if (token == null || !securityService.isTokenValid(token)) {
            return;
        }
        Integer userId = securityService.extractUserId(token);
        loggingService.logAction(userId, "LOGOUT", ipAddress);
        log.info("Logout: userId={}, ip={}", userId, ipAddress);
    }
}
