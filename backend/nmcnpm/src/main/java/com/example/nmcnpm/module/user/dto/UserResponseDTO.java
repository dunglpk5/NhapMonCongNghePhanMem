package com.example.nmcnpm.module.user.dto;

import com.example.nmcnpm.module.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * DTO trả về cho frontend khi liệt kê / xem chi tiết tài khoản.
 * KHÔNG bao giờ trả về password hash.
 */
@Getter
@Builder
public class UserResponseDTO {

    private Integer userId;
    private String username;
    private String email;
    private String fullName;
    private String role;
    private String status;
    private Integer failedLoginAttempts;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Map từ entity sang DTO (loại bỏ thông tin nhạy cảm).
     */
    public static UserResponseDTO from(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .status(user.getStatus())
                .failedLoginAttempts(user.getFailedLoginAttempts())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
