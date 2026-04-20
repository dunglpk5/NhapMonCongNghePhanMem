package com.example.nmcnpm.module.auth.service;

import com.example.nmcnpm.module.auth.dto.LoginRequest;
import com.example.nmcnpm.module.auth.dto.LoginResponse;

/**
 * IAuthService – interface của Auth Module.
 * Expose ra ngoài module để các thành phần khác (Controller) gọi vào.
 * Tương ứng với AuthService trong hồ sơ thiết kế.
 */
public interface IAuthService {

    /**
     * authenticate – điều phối toàn bộ luồng xác thực đăng nhập.
     * Tương ứng với AuthService.authenticate() trong hồ sơ thiết kế.
     *
     * @param request       DTO chứa emailOrUsername + password
     * @param ipAddress     IP của client (dùng cho audit log)
     * @return LoginResponse chứa JWT token + redirectUrl
     * @throws com.example.nmcnpm.module.core.exception.AuthException các trường hợp lỗi
     */
    LoginResponse authenticate(LoginRequest request, String ipAddress);

    /**
     * logout – vô hiệu hóa phiên JWT hiện tại.
     *
     * @param token     JWT token từ header Authorization
     * @param ipAddress IP của client
     */
    void logout(String token, String ipAddress);
}
