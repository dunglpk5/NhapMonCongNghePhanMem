package com.example.nmcnpm.module.core.exception;

/**
 * Tập hợp các exception dùng trong Auth Module.
 * Mỗi exception map với một thông báo lỗi cụ thể trong hồ sơ thiết kế.
 */
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

    /** Tài khoản không tìm thấy hoặc mật khẩu sai (không tiết lộ chi tiết). */
    public static class InvalidCredentialsException extends AuthException {
        public InvalidCredentialsException() {
            super("Sai tài khoản hoặc mật khẩu!");
        }
    }

    /** Tài khoản bị khóa tạm thời (NFR-13). */
    public static class AccountTemporarilyLockedException extends AuthException {
        private final long remainingSeconds;

        public AccountTemporarilyLockedException(long remainingSeconds) {
            super("Tài khoản tạm thời bị khóa. Vui lòng thử lại sau "
                    + remainingSeconds + " giây!");
            this.remainingSeconds = remainingSeconds;
        }

        public long getRemainingSeconds() { return remainingSeconds; }
    }

    /** Tài khoản bị khóa vĩnh viễn bởi admin. */
    public static class AccountPermanentlyLockedException extends AuthException {
        public AccountPermanentlyLockedException() {
            super("Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên!");
        }
    }

    /** JWT token không hợp lệ hoặc đã hết hạn. */
    public static class InvalidTokenException extends AuthException {
        public InvalidTokenException() {
            super("Token không hợp lệ hoặc đã hết hạn!");
        }
    }

    /** Phiên làm việc hết hạn do idle timeout (NFR-11). */
    public static class SessionExpiredException extends AuthException {
        public SessionExpiredException() {
            super("Phiên làm việc đã hết hạn. Vui lòng đăng nhập lại!");
        }
    }
}
