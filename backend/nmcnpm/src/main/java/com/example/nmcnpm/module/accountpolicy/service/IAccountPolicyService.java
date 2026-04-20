package com.example.nmcnpm.module.accountpolicy.service;

/**
 * IAccountPolicyService – interface của AccountPolicy Module.
 * Chính sách khóa tài khoản sau N lần đăng nhập sai (NFR-13).
 */
public interface IAccountPolicyService {

    /** Kiểm tra trạng thái khóa hiện tại của tài khoản. */
    LockStatus checkLockStatus(Integer userId);

    /**
     * Tăng failed attempts lên 1.
     * Nếu đủ 5 lần → tự động khóa tài khoản 15 phút.
     * Trả về số lần sai hiện tại.
     */
    int incrementFailedAttempts(Integer userId);

    /** Khóa tài khoản tạm thời X phút. */
    void lockAccount(Integer userId, int durationMinutes);

    /** Reset failed attempts về 0 sau đăng nhập thành công. */
    void resetFailedAttempts(Integer userId);

    /** DTO trạng thái khóa. */
    record LockStatus(boolean isLocked, long remainingSeconds) {}
}
