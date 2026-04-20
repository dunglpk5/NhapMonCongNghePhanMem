package com.example.nmcnpm.module.user.repository;

import com.example.nmcnpm.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository cho User entity.
 * Module: User Module – expose qua IUserService.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * findByIdentifier – tìm user theo email HOẶC username.
     * Dùng trong luồng đăng nhập (hồ sơ thiết kế: IUserService.findByIdentifier).
     */
    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.username = :identifier")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    /** Reset failed attempts sau đăng nhập thành công. */
    @Modifying
    @Query("UPDATE User u SET u.failedLoginAttempts = 0, u.lockedUntil = null WHERE u.userId = :userId")
    void resetFailedAttempts(@Param("userId") Integer userId);

    /** Tăng failed attempts và set lockedUntil nếu đủ 5 lần (NFR-13). */
    @Modifying
    @Query("UPDATE User u SET u.failedLoginAttempts = u.failedLoginAttempts + 1 WHERE u.userId = :userId")
    void incrementFailedAttempts(@Param("userId") Integer userId);

    /** Khóa tài khoản tạm thời. */
    @Modifying
    @Query("UPDATE User u SET u.lockedUntil = :lockedUntil WHERE u.userId = :userId")
    void lockAccountUntil(@Param("userId") Integer userId,
                          @Param("lockedUntil") LocalDateTime lockedUntil);
}
