package com.gtuschool.common.exception;

/**
 * Exception khi không tìm thấy tài nguyên trong hệ thống.
 * Ví dụ: không tìm thấy học sinh, lớp học theo ID.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s không tìm thấy với %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
