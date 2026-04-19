package com.example.nmcnpm.module.student.service;

import com.example.nmcnpm.module.student.dto.StudentDTO;

/**
 * Interface công khai của Student Module.
 * Các module khác (Report, Score...) chỉ giao tiếp qua interface này —
 * không được inject StudentServiceImpl trực tiếp.
 */
public interface IStudentService {

    /**
     * Thêm mới hồ sơ học sinh (FR-10, FR-11).
     * Bao gồm: sinh mã học sinh tự động, lưu DB, ghi log.
     *
     * @param dto      dữ liệu học sinh đã qua validation
     * @param userId   ID người thực hiện thao tác (để ghi log)
     * @return mã học sinh vừa tạo (ví dụ: "HS2024001")
     */
    String themHoSo(StudentDTO dto, Integer userId);

    /**
     * Kiểm tra học sinh tồn tại theo mã.
     * Dùng bởi Score Module khi nhập điểm.
     *
     * @param maHocSinh mã học sinh cần kiểm tra
     * @return true nếu tồn tại
     */
    boolean exists(String maHocSinh);
}
