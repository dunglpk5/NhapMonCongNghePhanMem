package com.example.nmcnpm.module.student.service.impl;

import com.example.nmcnpm.module.student.dto.StudentDTO;
import com.example.nmcnpm.module.student.dto.StudentResponseDTO;
import com.example.nmcnpm.module.student.entity.Student;
import com.example.nmcnpm.module.student.repository.StudentRepository;
import com.example.nmcnpm.module.student.service.IStudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Triển khai Student Module — chứa toàn bộ business logic.
 *
 * Modules tham gia (theo thiết kế):
 *  - Student Module (chính)
 *  - Logging Module — ghi audit log (FR-57) → TODO: inject ILoggingService
 *
 * Các module KHÁC không được inject class này trực tiếp.
 */
@Service
@Transactional
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;
    // TODO: inject ILoggingService khi Logging Module hoàn thiện
    // private final ILoggingService loggingService;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ── FR-10, FR-11: Thêm hồ sơ học sinh ───────────────────────────────

    @Override
    public String themHoSo(StudentDTO dto, Integer userId) {

        // Bước 5: Sinh mã học sinh tự động (FR-11)
        // Format: HS + 6 chữ số → HS000001
        int seq        = studentRepository.getNextSequence();
        String maHocSinh = String.format("HS%06d", seq);

        // Bước 6: Map DTO → Entity rồi lưu DB
        Student student = mapToEntity(dto);
        student.setStudentId(maHocSinh);
        studentRepository.save(student);

        // Bước 7: Ghi audit log (FR-57)
        // loggingService.logAction(userId, "ADD_STUDENT", maHocSinh);

        return maHocSinh;
    }

    // ── FR-12: Cập nhật hồ sơ ────────────────────────────────────────────

    @Override
    public void capNhatHoSo(String studentId, StudentDTO dto, Integer userId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Học sinh không tồn tại: " + studentId));

        // Chỉ cập nhật các trường được phép thay đổi
        student.setFullName(dto.getHoTen());
        student.setDateOfBirth(dto.getNgaySinh());
        student.setGender(mapGender(dto.getGioiTinh()));
        student.setAddress(dto.getDiaChi());
        student.setEthnicity(dto.getDanToc());
        student.setReligion(dto.getTonGiao());
        student.setFatherName(dto.getHoTenCha());
        student.setMotherName(dto.getHoTenMe());
        student.setPhone(dto.getSoDienThoai());

        studentRepository.save(student);

        // Ghi audit log
        // loggingService.logAction(userId, "UPDATE_STUDENT", studentId);
    }

    // ── FR-13, FR-14: Tìm kiếm ───────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> timKiem(
            String maHocSinh, String hoTen, Integer classId, Pageable pageable) {

        // Chuẩn hóa: chuỗi rỗng → null (để query JPQL bỏ qua điều kiện)
        String maNorm  = normalize(maHocSinh);
        String tenNorm = normalize(hoTen);

        return studentRepository
                .search(maNorm, tenNorm, classId, pageable)
                .map(StudentResponseDTO::from);
    }

    // ── FR-15: Lọc danh sách ─────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> locDanhSach(
            String hoTen, String danToc, String tonGiao,
            String hoTenCha, String hoTenMe, String sdt,
            Boolean gender, Pageable pageable) {

        return studentRepository
                .filter(normalize(hoTen), normalize(danToc), normalize(tonGiao),
                        normalize(hoTenCha), normalize(hoTenMe), normalize(sdt),
                        gender, pageable)
                .map(StudentResponseDTO::from);
    }

    // ── FR-36: Xem chi tiết ───────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO xemChiTiet(String studentId) {
        return studentRepository.findById(studentId)
                .map(StudentResponseDTO::from)
                .orElse(null);
    }

    // ── exists (dùng bởi Score / Conduct Module) ─────────────────────────

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String maHocSinh) {
        return studentRepository.existsById(maHocSinh);
    }

    // ── Private helpers ───────────────────────────────────────────────────

    /**
     * Map StudentDTO → Student entity.
     * studentId sẽ được set riêng sau khi sinh mã.
     */
    private Student mapToEntity(StudentDTO dto) {
        Student s = new Student();
        s.setFullName(dto.getHoTen());
        s.setDateOfBirth(dto.getNgaySinh());
        s.setGender(mapGender(dto.getGioiTinh()));
        s.setAddress(dto.getDiaChi());
        s.setEthnicity(dto.getDanToc());
        s.setReligion(dto.getTonGiao());
        s.setFatherName(dto.getHoTenCha());
        s.setMotherName(dto.getHoTenMe());
        s.setPhone(dto.getSoDienThoai());
        return s;
    }

    /**
     * Chuyển "Nam" → true, "Nữ" → false cho cột BIT trong DB.
     */
    private Boolean mapGender(String gioiTinh) {
        if (gioiTinh == null) return null;
        return "Nam".equalsIgnoreCase(gioiTinh.trim());
    }

    /**
     * Chuỗi rỗng / blank → null để JPQL bỏ qua điều kiện WHERE.
     */
    private String normalize(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}