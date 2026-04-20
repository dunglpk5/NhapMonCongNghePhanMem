package com.example.nmcnpm.module.student.service;

import com.example.nmcnpm.module.student.dto.StudentDTO;
import com.example.nmcnpm.module.student.dto.StudentresponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface công khai của Student Module.
 *
 * Các module khác (Score, Conduct, Report...) chỉ được inject
 * interface này — KHÔNG inject StudentServiceImpl trực tiếp.
 *
 * Phương thức theo FR tài liệu thiết kế:
 *  FR-10 → themHoSo
 *  FR-11 → (sinh mã nội bộ, không expose)
 *  FR-12 → capNhatHoSo
 *  FR-13 → timKiem
 *  FR-14 → (phân trang qua Pageable)
 *  FR-15 → locDanhSach
 *  FR-36 → xemChiTiet
 */
public interface IStudentService {

    /**
     * FR-10, FR-11: Thêm mới hồ sơ học sinh.
     * Tự động sinh mã theo format HS + năm + STT (vd: HS2024001)
     * và ghi audit log.
     *
     * @param dto    dữ liệu đã qua Validation Module
     * @param userId ID người thực hiện (để ghi log FR-57)
     * @return mã học sinh vừa tạo, ví dụ "HS2024001"
     */
    String themHoSo(StudentDTO dto, Integer userId);

    /**
     * FR-12: Cập nhật hồ sơ học sinh.
     *
     * @param studentId mã học sinh cần cập nhật
     * @param dto       thông tin mới
     * @param userId    ID người thực hiện (để ghi log)
     */
    void capNhatHoSo(String studentId, StudentDTO dto, Integer userId);

    /**
     * FR-13 + FR-14: Tìm kiếm học sinh theo mã, tên, lớp.
     * Pageable mặc định 20 bản ghi / trang (FR-14).
     *
     * @param maHocSinh mã học sinh (tùy chọn)
     * @param hoTen     họ tên (tùy chọn, tìm gần đúng)
     * @param classId   lớp học (tùy chọn)
     * @param pageable  thông tin phân trang
     */
    Page<StudentresponseDTO> timKiem(
            String maHocSinh, String hoTen, Integer classId, Pageable pageable);

    /**
     * FR-15: Lọc danh sách học sinh theo nhiều tiêu chí.
     */
    Page<StudentresponseDTO> locDanhSach(
            String hoTen, String danToc, String tonGiao,
            String hoTenCha, String hoTenMe, String sdt,
            Boolean gender, Pageable pageable);

    /**
     * FR-36: Xem chi tiết hồ sơ một học sinh.
     *
     * @param studentId mã học sinh
     * @return DTO chi tiết hoặc null nếu không tồn tại
     */
    StudentresponseDTO xemChiTiet(String studentId);

    /**
     * Kiểm tra học sinh tồn tại.
     * Dùng bởi Score Module (FR-22), Conduct Module (FR-29)
     * trước khi nhập điểm / hạnh kiểm.
     *
     * @param maHocSinh mã học sinh
     * @return true nếu tồn tại
     */
    boolean exists(String maHocSinh);
}
