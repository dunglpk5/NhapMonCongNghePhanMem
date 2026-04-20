package com.example.nmcnpm.module.validation.service;

import com.example.nmcnpm.module.student.dto.StudentDTO;
import com.example.nmcnpm.module.validation.dto.ValidationResult;

/**
 * Interface công khai của Validation Module.
 * Được inject vào Controller của các module khác.
 */
public interface IValidationService {

    /**
     * Kiểm tra tất cả trường bắt buộc không được bỏ trống (MS_01).
     * Áp dụng cho StudentDTO theo thiết kế FR-10.
     */
    ValidationResult validateRequired(StudentDTO dto);

    /**
     * Kiểm tra định dạng:
     *  - Họ tên: không chứa ký tự đặc biệt / số (MS_02)
     *  - Ngày sinh: không trong tương lai, tuổi 10–20 (MS_03)
     *  - Số điện thoại: 10 chữ số, bắt đầu bằng 0 (MS_04)
     */
    ValidationResult validateFormat(StudentDTO dto);

    /**
     * Kiểm tra tiêu chí tìm kiếm học sinh:
     *  - Ít nhất 1 trong 3 trường phải có giá trị (MS_01)
     *  - Mã HS đúng định dạng HS + số (MS_03)
     *  - Họ tên không chứa ký tự đặc biệt (MS_03)
     */
    ValidationResult validateSearchInput(String maHocSinh, String hoTen, Integer classId);
}