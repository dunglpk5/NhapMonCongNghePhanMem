package com.example.nmcnpm.module.user.controller;

import com.example.nmcnpm.module.core.response.ApiResponse;
import com.example.nmcnpm.module.user.dto.CreateUserRequest;
import com.example.nmcnpm.module.user.dto.UpdateUserRequest;
import com.example.nmcnpm.module.user.dto.UserResponseDTO;
import com.example.nmcnpm.module.user.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * UserController – REST controller cho User Module (Account Management).
 *
 * Tương ứng với các chức năng:
 *   FR-02: Thêm người dùng
 *   FR-03: Cập nhật thông tin người dùng
 *   FR-04: Khóa/Mở khóa tài khoản
 *   FR-05: Xóa tài khoản người dùng
 *   FR-06: Phân quyền người dùng
 *   FR-09: Đặt lại mật khẩu bởi Admin
 *
 * Tất cả endpoint yêu cầu role ADMIN.
 * Base path: /api/v1/users
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final IUserService userService;

    // ─── GET /api/v1/users — Danh sách users (có phân trang + tìm kiếm) ──

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> getUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<UserResponseDTO> result = userService.findAll(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách thành công", result));
    }

    // ─── GET /api/v1/users/{id} — Chi tiết user ──────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(ApiResponse.ok("OK", UserResponseDTO.from(user))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Người dùng không tồn tại!")));
    }

    // ─── POST /api/v1/users — Thêm user (FR-02) ─────────────────────────

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        try {
            UserResponseDTO created = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Tạo tài khoản thành công!", created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    // ─── PUT /api/v1/users/{id} — Cập nhật user (FR-03) ─────────────────

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateUserRequest request) {
        try {
            UserResponseDTO updated = userService.updateUser(id, request);
            return ResponseEntity.ok(ApiResponse.ok("Cập nhật thành công!", updated));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    // ─── PUT /api/v1/users/{id}/status — Khóa/Mở khóa (FR-04) ──────────

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserResponseDTO>> toggleStatus(@PathVariable Integer id) {
        try {
            UserResponseDTO result = userService.toggleStatus(id);
            String msg = "active".equals(result.getStatus())
                    ? "Mở khóa tài khoản thành công!"
                    : "Khóa tài khoản thành công!";
            return ResponseEntity.ok(ApiResponse.ok(msg, result));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    // ─── DELETE /api/v1/users/{id} — Xóa user (FR-05) ───────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.ok("Xóa tài khoản thành công!"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    // ─── PUT /api/v1/users/{id}/role — Phân quyền (FR-06) ───────────────

    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserResponseDTO>> changeRole(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        String newRole = body.get("role");
        if (newRole == null || newRole.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail("Vai trò không được để trống!"));
        }
        try {
            UserResponseDTO result = userService.changeRole(id, newRole);
            return ResponseEntity.ok(ApiResponse.ok("Phân quyền thành công!", result));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    // ─── PUT /api/v1/users/{id}/reset-password — Reset mật khẩu (FR-09) ─

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@PathVariable Integer id) {
        try {
            String newPassword = userService.resetPassword(id);
            return ResponseEntity.ok(ApiResponse.ok("Đặt lại mật khẩu thành công!", newPassword));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }
}
