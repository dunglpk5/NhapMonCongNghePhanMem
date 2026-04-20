package com.example.nmcnpm.module.auth.dto;

import com.example.nmcnpm.module.core.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

/**
 * LoginResponse DTO – trả về sau khi đăng nhập thành công.
 * Chứa JWT token và redirectUrl theo vai trò (FR-07).
 */
@Getter
@Builder
public class LoginResponse {

    private String  token;
    private String  tokenType;      // "Bearer"
    private String  redirectUrl;    // dashboard theo role (FR-07)
    private Integer userId;
    private String  username;
    private String  fullName;
    private String  role;
    private long    expiresIn;      // ms
}
