package com.example.nmcnpm.module.validation.service.impl;

import com.example.nmcnpm.module.student.dto.StudentDTO;
import com.example.nmcnpm.module.validation.dto.ValidationResult;
import com.example.nmcnpm.module.validation.service.IValidationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

/**
 * Triển khai Validation Module cho Student.
 * Tương ứng các unit: validateRequired, validateFormat, validateSearchInput
 * trong tài liệu thiết kế FR-10.
 */
@Service
public class StudentValidationServiceImpl implements IValidationService {

    // ── Bảng thông báo MS (FR-10) ─────────────────────────────────────────
    private static final String MS_01 = "Vui lòng nhập đầy đủ thông tin bắt buộc!";
    private static final String MS_02 = "Họ tên không hợp lệ!";
    private static final String MS_03 = "Ngày sinh không hợp lệ!";
    private static final String MS_04 = "Số điện thoại không hợp lệ!";

    // Regex: chỉ chữ cái tiếng Việt, khoảng trắng — không có số/ký tự đặc biệt
    private static final String NAME_REGEX = "^[\\p{L} ]+$";
    // Regex: đúng 10 chữ số, bắt đầu bằng 0
    private static final String PHONE_REGEX = "^0\\d{9}$";

    // ── validateRequired ──────────────────────────────────────────────────

    @Override
    public ValidationResult validateRequired(StudentDTO dto) {
        if (isBlank(dto.getHoTen()))         return ValidationResult.fail(MS_01);
        if (dto.getNgaySinh() == null)        return ValidationResult.fail(MS_01);
        if (isBlank(dto.getGioiTinh()))       return ValidationResult.fail(MS_01);
        if (isBlank(dto.getDiaChi()))         return ValidationResult.fail(MS_01);
        if (isBlank(dto.getDanToc()))         return ValidationResult.fail(MS_01);
        if (isBlank(dto.getTonGiao()))        return ValidationResult.fail(MS_01);
        if (isBlank(dto.getHoTenCha()))       return ValidationResult.fail(MS_01);
        if (isBlank(dto.getHoTenMe()))        return ValidationResult.fail(MS_01);
        if (isBlank(dto.getSoDienThoai()))    return ValidationResult.fail(MS_01);
        return ValidationResult.ok();
    }

    // ── validateFormat ────────────────────────────────────────────────────

    @Override
    public ValidationResult validateFormat(StudentDTO dto) {

        // Kiểm tra họ tên học sinh/cha/mẹ không chứa số hoặc ký tự đặc biệt
        if (!dto.getHoTen().matches(NAME_REGEX))
            return ValidationResult.fail(MS_02);
        if (!dto.getHoTenCha().matches(NAME_REGEX))
            return ValidationResult.fail(MS_02);
        if (!dto.getHoTenMe().matches(NAME_REGEX))
            return ValidationResult.fail(MS_02);

        // Kiểm tra ngày sinh: không trong tương lai, tuổi 10–20
        LocalDate dob  = dto.getNgaySinh();
        LocalDate today = LocalDate.now();
        if (dob.isAfter(today)) return ValidationResult.fail(MS_03);
        int age = Period.between(dob, today).getYears();
        if (age < 10 || age > 20) return ValidationResult.fail(MS_03);

        // Kiểm tra số điện thoại: 10 số, bắt đầu bằng 0
        if (!dto.getSoDienThoai().matches(PHONE_REGEX))
            return ValidationResult.fail(MS_04);

        return ValidationResult.ok();
    }

    // ── validateSearchInput ───────────────────────────────────────────────

    @Override
    public ValidationResult validateSearchInput(String maHocSinh, String hoTen, Integer classId) {
        boolean allEmpty = isBlank(maHocSinh) && isBlank(hoTen) && classId == null;
        if (allEmpty)
            return ValidationResult.fail("Vui lòng nhập ít nhất một tiêu chí tìm kiếm!");

        // Mã HS: nếu nhập phải bắt đầu bằng "HS"
        if (!isBlank(maHocSinh) && !maHocSinh.toUpperCase().startsWith("HS"))
            return ValidationResult.fail("Tiêu chí tìm kiếm không hợp lệ!");

        // Họ tên: nếu nhập không được chứa ký tự đặc biệt / số
        if (!isBlank(hoTen) && !hoTen.matches(NAME_REGEX))
            return ValidationResult.fail("Tiêu chí tìm kiếm không hợp lệ!");

        return ValidationResult.ok();
    }

    // ── Helper ────────────────────────────────────────────────────────────

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}