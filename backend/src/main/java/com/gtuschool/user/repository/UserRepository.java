package com.gtuschool.user.repository;

import com.gtuschool.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng users.
 * Cung cấp các truy vấn cơ bản cho User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    /**
     * Tìm users có vai trò teacher (dùng cho dropdown GVCN)
     */
    java.util.List<User> findByRole(String role);
}
