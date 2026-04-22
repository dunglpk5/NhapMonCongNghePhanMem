package com.example.nmcnpm.module.user.service;

import com.example.nmcnpm.module.core.enums.UserRole;
import com.example.nmcnpm.module.user.dto.CreateUserRequest;
import com.example.nmcnpm.module.user.dto.UpdateUserRequest;
import com.example.nmcnpm.module.user.dto.UserResponseDTO;
import com.example.nmcnpm.module.user.entity.User;
import com.example.nmcnpm.module.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

/**
 * UserServiceImpl – implementation của IUserService.
 * Cũng implement UserDetailsService để Spring Security có thể load user.
 *
 * Bao gồm:
 * - CRUD tài khoản (FR-02 → FR-06)
 * - Reset mật khẩu (FR-09)
 * - Phân quyền (FR-06)
 * - Tìm kiếm, phân trang
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_PASSWORD = "GtuSchool@2024";
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$";

    // ─── Lookup methods ──────────────────────────────────────────────────

    @Override
    public Optional<User> findByIdentifier(String identifier) {
        return userRepository.findByIdentifier(identifier);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    // ─── FR-02: Thêm người dùng ──────────────────────────────────────────

    @Override
    @Transactional
    public UserResponseDTO createUser(CreateUserRequest request) {
        // Kiểm tra email trùng
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại trong hệ thống!");
        }
        // Kiểm tra username trùng
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại!");
        }

        // Validate role
        UserRole role;
        try {
            role = UserRole.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + request.getRole());
        }

        // Mật khẩu: dùng mật khẩu được cung cấp hoặc mặc định
        String rawPassword = (request.getPassword() != null && !request.getPassword().isBlank())
                ? request.getPassword()
                : DEFAULT_PASSWORD;

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .role(role)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .status("active")
                .build();

        User saved = userRepository.save(user);
        log.info("Created user: id={}, username={}, role={}", saved.getUserId(), saved.getUsername(), saved.getRole());

        return UserResponseDTO.from(saved);
    }

    // ─── FR-03: Cập nhật thông tin người dùng ─────────────────────────────

    @Override
    @Transactional
    public UserResponseDTO updateUser(Integer userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Người dùng không tồn tại: " + userId));

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            // Kiểm tra email trùng (trừ chính user đang sửa)
            userRepository.findByEmail(request.getEmail())
                    .filter(u -> !u.getUserId().equals(userId))
                    .ifPresent(u -> {
                        throw new IllegalArgumentException("Email đã tồn tại!");
                    });
            user.setEmail(request.getEmail());
        }
        if (request.getRole() != null && !request.getRole().isBlank()) {
            try {
                user.setRole(UserRole.valueOf(request.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Vai trò không hợp lệ: " + request.getRole());
            }
        }
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            log.info("Updated password for userId={}", userId);
        }

        User saved = userRepository.save(user);
        log.info("Updated user: id={}", userId);
        return UserResponseDTO.from(saved);
    }

    // ─── FR-04: Khóa/Mở khóa tài khoản ──────────────────────────────────

    @Override
    @Transactional
    public UserResponseDTO toggleStatus(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Người dùng không tồn tại: " + userId));

        if ("active".equals(user.getStatus())) {
            user.setStatus("locked");
            log.info("Locked user: id={}", userId);
        } else {
            user.setStatus("active");
            user.setLockedUntil(null);
            user.setFailedLoginAttempts(0);
            log.info("Unlocked user: id={}", userId);
        }

        return UserResponseDTO.from(userRepository.save(user));
    }

    // ─── FR-05: Xóa tài khoản ────────────────────────────────────────────

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Người dùng không tồn tại: " + userId);
        }
        userRepository.deleteById(userId);
        log.info("Deleted user: id={}", userId);
    }

    // ─── FR-06: Phân quyền ────────────────────────────────────────────────

    @Override
    @Transactional
    public UserResponseDTO changeRole(Integer userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Người dùng không tồn tại: " + userId));

        UserRole role;
        try {
            role = UserRole.valueOf(newRole.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + newRole);
        }

        user.setRole(role);
        User saved = userRepository.save(user);
        log.info("Changed role: userId={}, newRole={}", userId, role);
        return UserResponseDTO.from(saved);
    }

    // ─── FR-09: Đặt lại mật khẩu ─────────────────────────────────────────

    @Override
    @Transactional
    public String resetPassword(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Người dùng không tồn tại: " + userId));

        String newPassword = generateRandomPassword(10);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        userRepository.save(user);

        log.info("Reset password for userId={}", userId);
        return newPassword;
    }

    // ─── Danh sách + tìm kiếm ────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(String keyword, Pageable pageable) {
        String normalizedKeyword = (keyword == null || keyword.isBlank()) ? null : keyword.trim();
        return userRepository.searchByKeyword(normalizedKeyword, pageable)
                .map(UserResponseDTO::from);
    }

    // ─── Spring Security UserDetailsService ───────────────────────────────

    /**
     * Spring Security gọi method này khi xác thực.
     * Tìm theo email HOẶC username (FR-01).
     */
    @Override
    public UserDetails loadUserByUsername(String identifier)
            throws UsernameNotFoundException {
        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + identifier));
    }

    // ─── Helpers ──────────────────────────────────────────────────────────

    private String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
