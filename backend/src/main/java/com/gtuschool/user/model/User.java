package com.gtuschool.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho bảng users trong database.
 * Chỉ define các fields cần thiết cho FK references từ Student và Class module.
 * Full CRUD cho User sẽ thuộc Auth Module (implement sau).
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    /**
     * Vai trò: admin, teacher, office_staff, principal, student
     * FR-06: Phân quyền theo 5 vai trò
     */
    @Column(name = "role", length = 20)
    private String role;

    /**
     * Trạng thái tài khoản: active, locked
     * FR-04: Khóa/Mở khóa tài khoản
     */
    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
