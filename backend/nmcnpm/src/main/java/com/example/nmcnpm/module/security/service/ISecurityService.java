package com.example.nmcnpm.module.security.service;

import com.example.nmcnpm.module.user.entity.User;

/**
 * ISecurityService – interface của Security Module.
 * Xử lý bcrypt (NFR-10) và JWT generation/validation.
 */
public interface ISecurityService {

    /**
     * So sánh mật khẩu raw với bcrypt hash đã lưu (NFR-10).
     * Tương ứng với verifyPassword trong hồ sơ thiết kế.
     */
    boolean verifyPassword(String rawPassword, String hashedPassword);

    /**
     * Tạo JWT token chứa userId, role, username.
     * Tương ứng với generateJwt trong hồ sơ thiết kế.
     */
    String generateJwt(User user);

    /**
     * Trích xuất userId từ JWT token.
     */
    Integer extractUserId(String token);

    /**
     * Trích xuất username từ JWT token.
     */
    String extractUsername(String token);

    /**
     * Kiểm tra token còn hợp lệ không (chưa expired, đúng chữ ký).
     */
    boolean isTokenValid(String token);

    /**
     * Mã hóa mật khẩu bằng bcrypt (dùng khi tạo/đổi mật khẩu).
     */
    String hashPassword(String rawPassword);
}
