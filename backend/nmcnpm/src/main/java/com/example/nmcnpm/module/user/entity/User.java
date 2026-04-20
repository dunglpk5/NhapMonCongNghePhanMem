package com.example.nmcnpm.module.user.entity;

import com.example.nmcnpm.module.core.enums.UserRole;
import com.example.nmcnpm.module.core.config.UserRoleConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entity User – ánh xạ trực tiếp từ bảng [users] trong DB.
 * Implements UserDetails để tích hợp với Spring Security.
 *
 * Lưu ý:
 *  - role lưu lowercase trong DB ('admin', 'teacher'...)
 *    nhưng Java enum dùng UPPERCASE → cần UserRoleConverter.
 *  - lockedUntil, revoked: Hibernate ddl-auto=update sẽ tự tạo cột.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /** Lưu dạng bcrypt hash (NFR-10). */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    /**
     * Role dùng custom converter để map:
     *   DB lowercase ('admin') ↔ Java UPPERCASE (ADMIN)
     */
    @Convert(converter = UserRoleConverter.class)
    @Column(nullable = false, length = 20)
    private UserRole role;

    /**
     * Trạng thái tài khoản: active | locked.
     * "locked" = khóa vĩnh viễn bởi admin (FR-04).
     */
    @Column(nullable = false, length = 10)
    @Builder.Default
    private String status = "active";

    @Column(nullable = true)
    @Builder.Default
    private Boolean revoked = false;

    /** Số lần đăng nhập sai liên tiếp (NFR-13). */
    @Column(name = "failed_login_attempts", nullable = false)
    @Builder.Default
    private Integer failedLoginAttempts = 0;

    /**
     * Thời điểm hết khóa tạm thời (NFR-13).
     * null = không bị khóa tạm.
     */
    @Column(name = "locked_until", nullable = true)
    private LocalDateTime lockedUntil;

    /** Thời điểm đăng nhập gần nhất. */
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ─── Spring Security UserDetails ──────────────────────────────────────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /** Spring Security dùng passwordHash để xác thực. */
    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired()  { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    /** Tài khoản active và không bị khóa vĩnh viễn. */
    @Override
    public boolean isAccountNonLocked() {
        return "active".equals(status);
    }

    @Override
    public boolean isEnabled() {
        return !revoked && "active".equals(status);
    }
}
