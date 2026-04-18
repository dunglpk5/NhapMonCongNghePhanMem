package com.gtuschool.common.exception;

/**
 * Exception khi dữ liệu bị trùng lặp.
 * Ví dụ: tạo lớp học trùng tên + khối + năm học.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
