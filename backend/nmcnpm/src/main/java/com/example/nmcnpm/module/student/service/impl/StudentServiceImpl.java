package com.example.nmcnpm.module.student.service.impl;

import com.example.nmcnpm.module.logging.service.ILoggingService;
import com.example.nmcnpm.module.student.dto.StudentDTO;
import com.example.nmcnpm.module.student.dto.StudentresponseDTO;
import com.example.nmcnpm.module.student.entity.Student;
import com.example.nmcnpm.module.student.repository.StudentRepository;
import com.example.nmcnpm.module.student.service.IStudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.time.LocalDate;

/**
 * Triển khai Student Module — chứa toàn bộ business logic.
 *
 * Modules tham gia (theo thiết kế):
 *  - Student Module (chính)
 *  - Logging Module — ghi audit log (FR-55)
 *
 * Các module KHÁC không được inject class này trực tiếp.
 */
@Service
@Transactional
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;
    private final ILoggingService loggingService;

    @PersistenceContext
    private EntityManager entityManager;

    public StudentServiceImpl(StudentRepository studentRepository,
                              ILoggingService loggingService) {
        this.studentRepository = studentRepository;
        this.loggingService = loggingService;
    }

    // ── FR-10, FR-11: Thêm hồ sơ học sinh ───────────────────────────────

    @Override
    public String themHoSo(StudentDTO dto, Integer userId) {

        // Bước 5: Sinh mã học sinh tự động (FR-11)
        String maHocSinh = generateMaHocSinh();

        // Bước 6: Map DTO → Entity rồi lưu DB
        Student student = saveHoSo(dto, maHocSinh);

        // Phân bổ lớp (tùy chọn)
        assignToClassIfPresent(student, dto.getClassId());

        // Bước 7: Ghi audit log (FR-55)
        loggingService.logAction(userId, "ADD_STUDENT", maHocSinh);

        return maHocSinh;
    }

    // ── FR-12: Cập nhật hồ sơ ────────────────────────────────────────────

    @Override
    public void capNhatHoSo(String studentId, StudentDTO dto, Integer userId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Học sinh không tồn tại: " + studentId));

        // Chỉ cập nhật các trường có gửi lên (not null)
        if (dto.getHoTen() != null && !dto.getHoTen().isEmpty()) {
            student.setFullName(dto.getHoTen());
        }
        if (dto.getNgaySinh() != null) {
            student.setDateOfBirth(dto.getNgaySinh());
        }
        if (dto.getGioiTinh() != null && !dto.getGioiTinh().isEmpty()) {
            student.setGender(mapGender(dto.getGioiTinh()));
        }
        if (dto.getDiaChi() != null && !dto.getDiaChi().isEmpty()) {
            student.setAddress(dto.getDiaChi());
        }
        if (dto.getDanToc() != null && !dto.getDanToc().isEmpty()) {
            student.setEthnicity(dto.getDanToc());
        }
        if (dto.getTonGiao() != null && !dto.getTonGiao().isEmpty()) {
            student.setReligion(dto.getTonGiao());
        }
        if (dto.getHoTenCha() != null && !dto.getHoTenCha().isEmpty()) {
            student.setFatherName(dto.getHoTenCha());
        }
        if (dto.getHoTenMe() != null && !dto.getHoTenMe().isEmpty()) {
            student.setMotherName(dto.getHoTenMe());
        }
        if (dto.getSoDienThoai() != null && !dto.getSoDienThoai().isEmpty()) {
            student.setPhone(dto.getSoDienThoai());
        }
        if (dto.getTrangThai() != null && !dto.getTrangThai().isEmpty()) {
            student.setStatus(dto.getTrangThai());
        }

        studentRepository.save(student);

        assignToClassIfPresent(student, dto.getClassId());

        // Ghi audit log
        loggingService.logAction(userId, "UPDATE_STUDENT", studentId);
    }

    // ── FR-13, FR-14: Tìm kiếm ───────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Page<StudentresponseDTO> timKiem(
            String maHocSinh, String hoTen, Integer classId, Pageable pageable) {

        // Chuẩn hóa: chuỗi rỗng → null (để query JPQL bỏ qua điều kiện)
        String maNorm  = normalize(maHocSinh);
        String tenNorm = normalize(hoTen);

        return studentRepository
                .search(maNorm, tenNorm, classId, pageable)
                .map(StudentresponseDTO::from);
    }

    // ── FR-15: Lọc danh sách ─────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Page<StudentresponseDTO> locDanhSach(
            String hoTen, String danToc, String tonGiao,
            String hoTenCha, String hoTenMe, String sdt,
            Boolean gender, Pageable pageable) {

        return studentRepository
                .filter(normalize(hoTen), normalize(danToc), normalize(tonGiao),
                        normalize(hoTenCha), normalize(hoTenMe), normalize(sdt),
                        gender, pageable)
                .map(StudentresponseDTO::from);
    }

    // ── FR-36: Xem chi tiết ───────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public StudentresponseDTO xemChiTiet(String studentId) {
        return studentRepository.findById(studentId)
                .map(StudentresponseDTO::from)
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
    private String generateMaHocSinh() {
        int year = LocalDate.now().getYear();
        int seq = studentRepository.getNextSequenceByYear(year);
        return String.format("HS%d%03d", year, seq);
    }

    private Student saveHoSo(StudentDTO dto, String maHocSinh) {
        Student student = mapToEntity(dto);
        student.setStudentId(maHocSinh);
        return studentRepository.save(student);
    }

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

    private void assignToClassIfPresent(Student student, Integer classId) {
        if (classId == null) return;
        List<com.example.nmcnpm.module.classes.entity.AcademicYear> years = 
            entityManager.createQuery("SELECT a FROM AcademicYear a WHERE a.isCurrent = true", com.example.nmcnpm.module.classes.entity.AcademicYear.class).getResultList();
        if (years.isEmpty()) return; 
        com.example.nmcnpm.module.classes.entity.AcademicYear currentYear = years.get(0);
        com.example.nmcnpm.module.classes.entity.ClassEntity classEntity = 
            entityManager.find(com.example.nmcnpm.module.classes.entity.ClassEntity.class, classId);
        if (classEntity == null) return;
        
        com.example.nmcnpm.module.student.entity.StudentClassId id = 
            new com.example.nmcnpm.module.student.entity.StudentClassId(student.getStudentId(), classId, currentYear.getYearId());
        com.example.nmcnpm.module.student.entity.StudentClass sc = 
            entityManager.find(com.example.nmcnpm.module.student.entity.StudentClass.class, id);
        if (sc == null) {
            sc = new com.example.nmcnpm.module.student.entity.StudentClass();
            sc.setId(id);
            sc.setStudent(student);
            sc.setClassEntity(classEntity);
            sc.setAcademicYear(currentYear);
            entityManager.persist(sc);
        }
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
