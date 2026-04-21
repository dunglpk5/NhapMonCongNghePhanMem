package com.example.nmcnpm.module.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * LoginRequest DTO – dữ liệu đầu vào từ form đăng nhập.
 * Tương ứng với txtEmail + txtPassword trong hồ sơ thiết kế.
 */
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Không được để trống email!")
    private String email;

    @NotBlank(message = "Không được để trống mật khẩu!")
    private String password;
}
