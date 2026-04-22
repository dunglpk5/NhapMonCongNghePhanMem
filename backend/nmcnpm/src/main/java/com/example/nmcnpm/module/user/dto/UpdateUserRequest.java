package com.example.nmcnpm.module.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO nhận dữ liệu khi Admin cập nhật tài khoản (FR-03).
 * Tất cả field là optional — chỉ cập nhật những field được gửi.
 */
@Getter
@Setter
public class UpdateUserRequest {

    private String fullName;

    @Email(message = "Email không hợp lệ!")
    private String email;

    private String role;

    private String status;

    /** Mật khẩu mới (tùy chọn). Nếu null hoặc blank thì không cập nhật. */
    private String password;
}
