package com.example.nmcnpm.module.accountpolicy.service;

import com.example.nmcnpm.module.user.entity.User;
import com.example.nmcnpm.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * AccountPolicyServiceImpl – xử lý logic khóa tài khoản (NFR-13).
 * Khóa tạm 15 phút sau 5 lần đăng nhập sai liên tiếp.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountPolicyServiceImpl implements IAccountPolicyService {

    private final UserRepository userRepository;

    @Value("${app.security.max-failed-attempts}")
    private int maxFailedAttempts;

    @Value("${app.security.lock-duration-minutes}")
    private int lockDurationMinutes;

    /**
     * checkLockStatus – kiểm tra tài khoản có đang bị khóa tạm không.
     * Logic: lockedUntil != null AND lockedUntil > now → đang bị khóa.
     */
    @Override
    @Transactional(readOnly = true)
    public LockStatus checkLockStatus(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new LockStatus(false, 0);
        }
        User user = userOpt.get();
        LocalDateTime lockedUntil = user.getLockedUntil();

        if (lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now())) {
            long remaining = lockedUntil.toEpochSecond(ZoneOffset.UTC)
                    - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            return new LockStatus(true, remaining);
        }
        return new LockStatus(false, 0);
    }

    /**
     * incrementFailedAttempts – tăng số lần sai.
     * Nếu đủ maxFailedAttempts (5) → gọi lockAccount.
     */
    @Override
    @Transactional
    public int incrementFailedAttempts(Integer userId) {
        userRepository.incrementFailedAttempts(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        int currentAttempts = user.getFailedLoginAttempts();
        log.warn("User {} failed login attempt #{}", userId, currentAttempts);

        if (currentAttempts >= maxFailedAttempts) {
            lockAccount(userId, lockDurationMinutes);
        }
        return currentAttempts;
    }

    /** lockAccount – đặt lockedUntil = now + durationMinutes (NFR-13). */
    @Override
    @Transactional
    public void lockAccount(Integer userId, int durationMinutes) {
        LocalDateTime lockedUntil = LocalDateTime.now().plusMinutes(durationMinutes);
        userRepository.lockAccountUntil(userId, lockedUntil);
        log.warn("User {} locked until {}", userId, lockedUntil);
    }

    /** resetFailedAttempts – xóa lock sau đăng nhập thành công. */
    @Override
    @Transactional
    public void resetFailedAttempts(Integer userId) {
        userRepository.resetFailedAttempts(userId);
    }
}
