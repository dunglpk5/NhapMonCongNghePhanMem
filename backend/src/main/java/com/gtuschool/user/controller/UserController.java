package com.gtuschool.user.controller;

import com.gtuschool.common.dto.ApiResponse;
import com.gtuschool.user.model.User;
import com.gtuschool.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User Controller - Cung cấp thông tin người dùng cho các module khác.
 * Full CRUD sẽ thuộc Auth Module, controller này chỉ expose endpoint cần thiết.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    /**
     * Lấy danh sách giáo viên để hiển thị trong dropdown chọn GVCN.
     * GET /api/users?role=teacher
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersByRole(
            @RequestParam(value = "role", required = false) String role) {
        List<User> users = (role != null && !role.isBlank())
                ? userRepository.findByRole(role)
                : userRepository.findAll();

        List<UserDTO> result = users.stream()
                .filter(u -> "active".equals(u.getStatus()))
                .map(u -> new UserDTO(u.getUserId(), u.getFullName(), u.getRole(), u.getEmail()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Thành công", result));
    }

    /**
     * Lấy chi tiết người dùng theo ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(u -> ResponseEntity.ok(ApiResponse.success("Thành công",
                        new UserDTO(u.getUserId(), u.getFullName(), u.getRole(), u.getEmail()))))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DTO nhỏ gọn để trả về thông tin user (không expose password_hash)
     */
    public record UserDTO(Integer userId, String fullName, String role, String email) {}
}
