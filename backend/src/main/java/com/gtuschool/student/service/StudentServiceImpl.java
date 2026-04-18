package com.gtuschool.student.service;

import com.gtuschool.audit.service.ILoggingService;
import com.gtuschool.common.dto.PagedResponse;
import com.gtuschool.common.exception.BusinessRuleException;
import com.gtuschool.common.exception.ResourceNotFoundException;
import com.gtuschool.common.validation.ValidationService;
import com.gtuschool.student.dto.CreateStudentRequest;
import com.gtuschool.student.dto.StudentDTO;
import com.gtuschool.student.dto.UpdateStudentRequest;
import com.gtuschool.student.model.Student;
import com.gtuschool.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Student Module Service Implementation.
 * Chứa toàn bộ business logic cho quản lý hồ sơ học sinh.
 *
 * FR-10: Thêm hồ sơ học sinh
 * FR-11: Tạo mã học sinh tự động
 * FR-12: Cập nhật hồ sơ
 * FR-13: Tìm kiếm
 * FR-14: Phân trang (20/trang)
 * FR-15: Lọc danh sách
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;
    private final ValidationService validationService;
    private final ILoggingService loggingService;

    /**
     * FR-10, FR-11: Thêm hồ sơ học sinh mới
     * Process (theo báo cáo):
     * 1. Kiểm tra dữ liệu đầu vào
     * 2. Validate định dạng (họ tên, ngày sinh, SĐT)
     * 3. Sinh mã HS tự động
     * 4. Lưu vào DB
     * 5. Ghi log (FR-57)
     */
    @Override
    public StudentDTO createStudent(CreateStudentRequest request) {
        // Bước 2: Validate dữ liệu theo quy tắc nghiệp vụ
        List<String> errors = validationService.validateStudentData(
                request.getFullName(),
                request.getDateOfBirth(),
                request.getPhone(),
                request.getFatherName(),
                request.getMotherName()
        );
        if (!errors.isEmpty()) {
            throw new BusinessRuleException(String.join("; ", errors));
        }

        // Bước 3: Sinh mã học sinh tự động (FR-11)
        String studentId = generateStudentId();

        // Bước 4: Tạo entity và lưu
        Student student = Student.builder()
                .studentId(studentId)
                .fullName(request.getFullName().trim())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .address(request.getAddress().trim())
                .ethnicity(request.getEthnicity().trim())
                .religion(request.getReligion().trim())
                .fatherName(request.getFatherName().trim())
                .motherName(request.getMotherName().trim())
                .phone(request.getPhone().trim())
                .build();

        Student saved = studentRepository.save(student);
        log.info("Tạo hồ sơ học sinh thành công: {}", studentId);

        // Bước 5: Ghi log (FR-57)
        loggingService.logInsert("students", null,
                String.format("{\"student_id\":\"%s\",\"full_name\":\"%s\"}",
                        saved.getStudentId(), saved.getFullName()));

        return mapToDTO(saved);
    }

    /**
     * FR-12: Cập nhật hồ sơ học sinh
     */
    @Override
    public StudentDTO updateStudent(String studentId, UpdateStudentRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Học sinh", "mã", studentId));

        String oldData = String.format("{\"full_name\":\"%s\"}", student.getFullName());

        // Cập nhật chỉ các field được gửi lên (partial update)
        if (request.getFullName() != null) {
            if (!validationService.isValidName(request.getFullName())) {
                throw new BusinessRuleException("Họ tên không hợp lệ!");
            }
            student.setFullName(request.getFullName().trim());
        }
        if (request.getDateOfBirth() != null) {
            if (!validationService.isValidDateOfBirth(request.getDateOfBirth())) {
                throw new BusinessRuleException("Ngày sinh không hợp lệ!");
            }
            student.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            student.setGender(request.getGender());
        }
        if (request.getAddress() != null) {
            student.setAddress(request.getAddress().trim());
        }
        if (request.getEthnicity() != null) {
            student.setEthnicity(request.getEthnicity().trim());
        }
        if (request.getReligion() != null) {
            student.setReligion(request.getReligion().trim());
        }
        if (request.getFatherName() != null) {
            if (!validationService.isValidName(request.getFatherName())) {
                throw new BusinessRuleException("Họ tên cha không hợp lệ!");
            }
            student.setFatherName(request.getFatherName().trim());
        }
        if (request.getMotherName() != null) {
            if (!validationService.isValidName(request.getMotherName())) {
                throw new BusinessRuleException("Họ tên mẹ không hợp lệ!");
            }
            student.setMotherName(request.getMotherName().trim());
        }
        if (request.getPhone() != null) {
            if (!validationService.isValidPhone(request.getPhone())) {
                throw new BusinessRuleException("Số điện thoại không hợp lệ!");
            }
            student.setPhone(request.getPhone().trim());
        }

        Student updated = studentRepository.save(student);
        log.info("Cập nhật hồ sơ học sinh thành công: {}", studentId);

        // Ghi log (FR-57)
        String newData = String.format("{\"full_name\":\"%s\"}", updated.getFullName());
        loggingService.logUpdate("students", null, oldData, newData);

        return mapToDTO(updated);
    }

    /**
     * Xem chi tiết hồ sơ học sinh
     */
    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Học sinh", "mã", studentId));
        return mapToDTO(student);
    }

    /**
     * FR-13, FR-14: Tìm kiếm + phân trang
     */
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<StudentDTO> searchStudents(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Student> studentPage;
        if (keyword == null || keyword.trim().isEmpty()) {
            studentPage = studentRepository.findAll(pageable);
        } else {
            studentPage = studentRepository.searchByKeyword(keyword.trim(), pageable);
        }

        List<StudentDTO> content = studentPage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return PagedResponse.of(content, page, size,
                studentPage.getTotalElements(), studentPage.getTotalPages());
    }

    /**
     * FR-15: Lọc danh sách theo nhiều tiêu chí
     */
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<StudentDTO> filterStudents(String fullName, Boolean gender, String address,
                                                     String ethnicity, String religion,
                                                     String fatherName, String motherName,
                                                     String phone, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Student> studentPage = studentRepository.filterStudents(
                fullName, gender, address, ethnicity, religion,
                fatherName, motherName, phone, pageable);

        List<StudentDTO> content = studentPage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return PagedResponse.of(content, page, size,
                studentPage.getTotalElements(), studentPage.getTotalPages());
    }

    /**
     * FR-11: Sinh mã học sinh tự động
     * Format: HS + 6 chữ số (HS000001, HS000002, ...)
     * Tương tự stored procedure sp_CreateStudent trong DB
     */
    @Override
    public String generateStudentId() {
        String maxId = studentRepository.findMaxStudentId().orElse(null);
        int nextNumber = 1;

        if (maxId != null && maxId.startsWith("HS")) {
            try {
                nextNumber = Integer.parseInt(maxId.substring(2)) + 1;
            } catch (NumberFormatException e) {
                log.warn("Không parse được mã HS cuối cùng: {}", maxId);
            }
        }

        return String.format("HS%06d", nextNumber);
    }

    /**
     * Chuyển đổi Student entity sang StudentDTO
     */
    private StudentDTO mapToDTO(Student student) {
        return StudentDTO.builder()
                .studentId(student.getStudentId())
                .fullName(student.getFullName())
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender() != null && student.getGender() ? "Nam" : "Nữ")
                .address(student.getAddress())
                .ethnicity(student.getEthnicity())
                .religion(student.getReligion())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .phone(student.getPhone())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
