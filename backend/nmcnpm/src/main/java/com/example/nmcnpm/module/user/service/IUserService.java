package com.example.nmcnpm.module.user.service;

import com.example.nmcnpm.module.user.entity.User;

import java.util.Optional;

/**
 * IUserService – interface của User Module.
 * Các module khác (Auth, AccountPolicy) chỉ gọi qua interface này,
 * không phụ thuộc trực tiếp vào implementation.
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
}
