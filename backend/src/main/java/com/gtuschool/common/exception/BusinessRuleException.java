package com.gtuschool.common.exception;

/**
 * Exception khi vi phạm quy tắc nghiệp vụ.
 * Ví dụ: xóa lớp còn học sinh, GV chủ nhiệm nhiều lớp cùng năm.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
