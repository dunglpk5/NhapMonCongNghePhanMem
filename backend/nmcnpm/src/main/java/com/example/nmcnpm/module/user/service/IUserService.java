package com.example.nmcnpm.module.user.service;

import com.example.nmcnpm.module.user.dto.CreateUserRequest;
import com.example.nmcnpm.module.user.dto.UpdateUserRequest;
import com.example.nmcnpm.module.user.dto.UserResponseDTO;
import com.example.nmcnpm.module.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * IUserService – interface của User Module.
 * Các module khác (Auth, AccountPolicy) chỉ gọi qua interface này,
 * không phụ thuộc trực tiếp vào implementation.
 *
 * FR-02 → createUser
 * FR-03 → updateUser
 * FR-04 → toggleStatus
 * FR-05 → deleteUser
 * FR-06 → changeRole
 * FR-09 → resetPassword
 */
public interface IUserService {

    /**
     * Tìm user theo email hoặc username.
     * Dùng ở bước xác thực đăng nhập.
     */
    Optional<User> findByIdentifier(String identifier);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    // ─── CRUD (FR-02 → FR-06, FR-09) ─────────────────────────────────────

    /** FR-02: Thêm người dùng. */
    UserResponseDTO createUser(CreateUserRequest request);

    /** FR-03: Cập nhật thông tin người dùng. */
    UserResponseDTO updateUser(Integer userId, UpdateUserRequest request);

    /** FR-04: Khóa/Mở khóa tài khoản. */
    UserResponseDTO toggleStatus(Integer userId);

    /** FR-05: Xóa tài khoản người dùng. */
    void deleteUser(Integer userId);

    /** FR-06: Phân quyền người dùng. */
    UserResponseDTO changeRole(Integer userId, String newRole);

    /** FR-09: Đặt lại mật khẩu bởi Admin. */
    String resetPassword(Integer userId);

    /** Danh sách users có phân trang + tìm kiếm. */
    Page<UserResponseDTO> findAll(String keyword, Pageable pageable);

    /** Lưu trực tiếp entity (dùng nội bộ bởi DataSeeder). */
    User save(User user);
}
