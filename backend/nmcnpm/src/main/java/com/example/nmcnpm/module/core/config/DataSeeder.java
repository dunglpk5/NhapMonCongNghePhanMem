package com.example.nmcnpm.module.core.config;

import com.example.nmcnpm.module.core.enums.UserRole;
import com.example.nmcnpm.module.user.entity.User;
import com.example.nmcnpm.module.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataSeeder – tạo tài khoản mặc định khi khởi động ứng dụng.
 *
 * Chỉ tạo nếu username chưa tồn tại trong DB.
 * Dùng để test các chức năng:
 *   - Quản lý tài khoản & phân quyền (FR-01 → FR-09)
 *   - Quản lý hồ sơ học sinh (FR-10 → FR-15)
 *
 * Tài khoản test:
 *   admin      / Admin@123      → ADMIN        (full quyền)
 *   teacher01  / Teacher@123    → TEACHER       (nhập điểm, xem lớp)
 *   staff01    / Staff@123      → OFFICE_STAFF  (quản lý HS, lớp)
 *   principal01/ Principal@123  → PRINCIPAL     (xem báo cáo)
 *   student01  / Student@123    → STUDENT       (xem hồ sơ cá nhân)
 */
@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("══════════════════════════════════════════════════════");
        log.info("  DataSeeder — Kiểm tra và tạo tài khoản test...");
        log.info("══════════════════════════════════════════════════════");

        createIfNotExists("admin",        "admin@school.edu.vn",
                "Quản trị viên hệ thống",    UserRole.ADMIN,        "Admin@123");

        createIfNotExists("teacher01",    "teacher01@school.edu.vn",
                "Nguyễn Văn Thầy",           UserRole.TEACHER,      "Teacher@123");

        createIfNotExists("staff01",      "staff01@school.edu.vn",
                "Trần Thị Nhân Viên",        UserRole.OFFICE_STAFF, "Staff@123");

        createIfNotExists("principal01",  "principal01@school.edu.vn",
                "Lê Văn Hiệu Trưởng",       UserRole.PRINCIPAL,    "Principal@123");

        createIfNotExists("student01",    "student01@school.edu.vn",
                "Phạm Minh Học Sinh",        UserRole.STUDENT,      "Student@123");

        log.info("══════════════════════════════════════════════════════");
        log.info("  DataSeeder — Hoàn tất!");
        log.info("══════════════════════════════════════════════════════");
    }

    private void createIfNotExists(String username, String email,
                                   String fullName, UserRole role, String rawPassword) {
        if (userService.existsByUsername(username)) {
            log.info("  [SKIP] {} — đã tồn tại", username);
            return;
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .fullName(fullName)
                .role(role)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .status("active")
                .build();

        userService.save(user);
        log.info("  [CREATED] {} / {} → role={}", username, rawPassword, role);
    }
}
