package com.example.nmcnpm.module.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO nhận dữ liệu khi Admin tạo tài khoản mới (FR-02).
 */
@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "Không được để trống tên đăng nhập!")
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải từ 3-50 ký tự!")
    private String username;

    @NotBlank(message = "Không được để trống email!")
    @Email(message = "Email không hợp lệ!")
    private String email;

    @NotBlank(message = "Không được để trống họ tên!")
    private String fullName;

    @NotBlank(message = "Không được để trống vai trò!")
    private String role;

    /** Mật khẩu tùy chọn — nếu không nhập, hệ thống sinh mật khẩu mặc định. */
    private String password;
}
