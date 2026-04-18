package com.gtuschool.common.validation;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validation Module - Kiểm tra dữ liệu đầu vào.
 * Theo thiết kế trong báo cáo: IValidationService
 * NFR-18: Thông báo lỗi cụ thể bằng tiếng Việt.
 */
@Service
public class ValidationService {

    // Pattern: Họ tên chỉ chứa chữ cái (Unicode) và khoảng trắng
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L}\\s]+$");

    // Pattern: Số điện thoại VN - 10 số, bắt đầu bằng 0
    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");

    // Pattern: Tên lớp (ví dụ: 10A1, 11B2, 12C3)
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("^(10|11|12)[A-Z]\\d{1,2}$");

    // Pattern: Năm học (ví dụ: 2024-2025, 2025-2026)
    private static final Pattern ACADEMIC_YEAR_PATTERN = Pattern.compile("^\\d{4}\\s*-\\s*\\d{4}$");

    /**
     * Validate họ tên: không chứa ký tự đặc biệt hoặc số (MS_02 trong báo cáo)
     *
     * @param name Họ tên cần kiểm tra
     * @return true nếu hợp lệ
     */
    public boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validate ngày sinh: tuổi từ 10 đến 20 (MS_03 trong báo cáo)
     *
     * @param dateOfBirth Ngày sinh cần kiểm tra
     * @return true nếu hợp lệ
     */
    public boolean isValidDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return false;
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            return false;
        }
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return age >= 10 && age <= 20;
    }

    /**
     * Validate số điện thoại: 10 số, bắt đầu bằng 0 (MS_04 trong báo cáo)
     *
     * @param phone Số điện thoại cần kiểm tra
     * @return true nếu hợp lệ
     */
    public boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validate tên lớp: format 10A1, 11B2, 12C3...
     *
     * @param className Tên lớp cần kiểm tra
     * @return true nếu hợp lệ
     */
    public boolean isValidClassName(String className) {
        if (className == null || className.trim().isEmpty()) {
            return false;
        }
        return CLASS_NAME_PATTERN.matcher(className.trim()).matches();
    }

    /**
     * Validate năm học: format YYYY-YYYY, năm sau = năm trước + 1
     *
     * @param yearName Tên năm học cần kiểm tra
     * @return true nếu hợp lệ
     */
    public boolean isValidAcademicYear(String yearName) {
        if (yearName == null || yearName.trim().isEmpty()) {
            return false;
        }
        if (!ACADEMIC_YEAR_PATTERN.matcher(yearName.trim()).matches()) {
            return false;
        }
        String[] parts = yearName.trim().split("\\s*-\\s*");
        int startYear = Integer.parseInt(parts[0]);
        int endYear = Integer.parseInt(parts[1]);
        return endYear == startYear + 1;
    }

    /**
     * Validate khối: phải nằm trong [10, 11, 12] (THPT)
     *
     * @param gradeLevel Khối cần kiểm tra
     * @return true nếu hợp lệ
     */
    public boolean isValidGradeLevel(Integer gradeLevel) {
        return gradeLevel != null && gradeLevel >= 10 && gradeLevel <= 12;
    }

    /**
     * Validate sĩ số: số nguyên dương, tối đa 60
     *
     * @param size Sĩ số cần kiểm tra
     * @return true nếu hợp lệ
     */
    public boolean isValidClassSize(Integer size) {
        return size != null && size >= 0 && size <= 60;
    }

    /**
     * Validate toàn bộ thông tin học sinh và trả về danh sách lỗi
     *
     * @param fullName    Họ tên
     * @param dateOfBirth Ngày sinh
     * @param phone       Số điện thoại
     * @param fatherName  Họ tên cha
     * @param motherName  Họ tên mẹ
     * @return Danh sách lỗi (rỗng nếu hợp lệ)
     */
    public List<String> validateStudentData(String fullName, LocalDate dateOfBirth,
                                             String phone, String fatherName, String motherName) {
        List<String> errors = new ArrayList<>();

        if (!isValidName(fullName)) {
            errors.add("Họ tên học sinh không hợp lệ!");
        }
        if (!isValidDateOfBirth(dateOfBirth)) {
            errors.add("Ngày sinh không hợp lệ!");
        }
        if (!isValidPhone(phone)) {
            errors.add("Số điện thoại không hợp lệ!");
        }
        if (fatherName != null && !fatherName.trim().isEmpty() && !isValidName(fatherName)) {
            errors.add("Họ tên cha không hợp lệ!");
        }
        if (motherName != null && !motherName.trim().isEmpty() && !isValidName(motherName)) {
            errors.add("Họ tên mẹ không hợp lệ!");
        }

        return errors;
    }
}
