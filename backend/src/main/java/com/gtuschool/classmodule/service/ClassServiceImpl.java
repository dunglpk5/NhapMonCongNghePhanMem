package com.gtuschool.classmodule.service;

import com.gtuschool.academicyear.model.AcademicYear;
import com.gtuschool.academicyear.repository.AcademicYearRepository;
import com.gtuschool.audit.service.ILoggingService;
import com.gtuschool.classmodule.dto.AssignTeacherRequest;
import com.gtuschool.classmodule.dto.ClassDTO;
import com.gtuschool.classmodule.dto.CreateClassRequest;
import com.gtuschool.classmodule.dto.UpdateClassRequest;
import com.gtuschool.classmodule.model.ClassEntity;
import com.gtuschool.classmodule.repository.ClassRepository;
import com.gtuschool.common.dto.PagedResponse;
import com.gtuschool.common.exception.BusinessRuleException;
import com.gtuschool.common.exception.DuplicateResourceException;
import com.gtuschool.common.exception.ResourceNotFoundException;
import com.gtuschool.common.validation.ValidationService;
import com.gtuschool.student.repository.StudentClassRepository;
import com.gtuschool.user.model.User;
import com.gtuschool.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class Module Service Implementation.
 * Chứa toàn bộ business logic cho quản lý lớp học.
 *
 * FR-16: Tạo lớp học
 * FR-17: Cập nhật lớp
 * FR-18: Xóa lớp (khi 0 HS)
 * FR-19: Phân công GVCN
 * FR-20: Kiểm tra trùng GVCN
 * FR-21: Lọc danh sách lớp
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClassServiceImpl implements IClassService {

    private final ClassRepository classRepository;
    private final AcademicYearRepository academicYearRepository;
    private final UserRepository userRepository;
    private final StudentClassRepository studentClassRepository;
    private final ValidationService validationService;
    private final ILoggingService loggingService;

    /**
     * FR-16: Tạo lớp học mới
     * Process (theo báo cáo):
     * 1. Kiểm tra dữ liệu đầu vào
     * 2. Validate định dạng (tên lớp, khối, năm học)
     * 3. Kiểm tra trùng (tên + khối + năm)
     * 4. Lưu vào DB
     * 5. Ghi log (FR-57)
     */
    @Override
    public ClassDTO createClass(CreateClassRequest request) {
        // Validate tên lớp
        if (!validationService.isValidClassName(request.getClassName())) {
            throw new BusinessRuleException("Tên lớp không hợp lệ! Format: 10A1, 11B2, 12C3...");
        }

        // Validate khối
        if (!validationService.isValidGradeLevel(request.getGradeLevel())) {
            throw new BusinessRuleException("Khối không hợp lệ! Phải từ 10 đến 12.");
        }

        // Kiểm tra năm học tồn tại
        AcademicYear academicYear = academicYearRepository.findById(request.getYearId())
                .orElseThrow(() -> new ResourceNotFoundException("Năm học", "ID", request.getYearId()));

        // Kiểm tra trùng lớp (cùng tên + khối + năm học)
        Optional<ClassEntity> existing = classRepository
                .findByClassNameAndGradeLevelAndAcademicYear_YearId(
                        request.getClassName(), request.getGradeLevel(), request.getYearId());
        if (existing.isPresent()) {
            throw new DuplicateResourceException("Lớp học đã tồn tại trong hệ thống!");
        }

        // Tạo entity
        ClassEntity classEntity = ClassEntity.builder()
                .className(request.getClassName().trim())
                .gradeLevel(request.getGradeLevel())
                .academicYear(academicYear)
                .build();

        // Phân công GVCN nếu có
        if (request.getHomeroomTeacherId() != null) {
            User teacher = findAndValidateTeacher(request.getHomeroomTeacherId(), request.getYearId());
            classEntity.setHomeroomTeacher(teacher);
        }

        ClassEntity saved = classRepository.save(classEntity);
        log.info("Tạo lớp học thành công: {} - Khối {} - Năm {}",
                saved.getClassName(), saved.getGradeLevel(), academicYear.getYearName());

        // Ghi log (FR-57)
        loggingService.logInsert("classes", null,
                String.format("{\"class_id\":%d,\"class_name\":\"%s\",\"grade_level\":%d}",
                        saved.getClassId(), saved.getClassName(), saved.getGradeLevel()));

        return mapToDTO(saved);
    }

    /**
     * FR-17: Cập nhật thông tin lớp
     */
    @Override
    public ClassDTO updateClass(Integer classId, UpdateClassRequest request) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Lớp học", "ID", classId));

        String oldData = String.format("{\"class_name\":\"%s\"}", classEntity.getClassName());

        if (request.getClassName() != null) {
            if (!validationService.isValidClassName(request.getClassName())) {
                throw new BusinessRuleException("Tên lớp không hợp lệ!");
            }
            classEntity.setClassName(request.getClassName().trim());
        }

        if (request.getGradeLevel() != null) {
            if (!validationService.isValidGradeLevel(request.getGradeLevel())) {
                throw new BusinessRuleException("Khối không hợp lệ! Phải từ 10 đến 12.");
            }
            classEntity.setGradeLevel(request.getGradeLevel());
        }

        if (request.getYearId() != null) {
            AcademicYear year = academicYearRepository.findById(request.getYearId())
                    .orElseThrow(() -> new ResourceNotFoundException("Năm học", "ID", request.getYearId()));
            classEntity.setAcademicYear(year);
        }

        if (request.getHomeroomTeacherId() != null) {
            Integer yearId = classEntity.getAcademicYear().getYearId();
            User teacher = findAndValidateTeacher(request.getHomeroomTeacherId(), yearId, classId);
            classEntity.setHomeroomTeacher(teacher);
        }

        ClassEntity updated = classRepository.save(classEntity);
        log.info("Cập nhật lớp học thành công: ID={}", classId);

        String newData = String.format("{\"class_name\":\"%s\"}", updated.getClassName());
        loggingService.logUpdate("classes", null, oldData, newData);

        return mapToDTO(updated);
    }

    /**
     * FR-18: Xóa lớp (chỉ khi 0 học sinh)
     */
    @Override
    public void deleteClass(Integer classId) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Lớp học", "ID", classId));

        // Kiểm tra lớp còn học sinh không
        long studentCount = studentClassRepository.countByClassId(classId);
        if (studentCount > 0) {
            throw new BusinessRuleException(
                    String.format("Không thể xóa lớp %s vì còn %d học sinh!",
                            classEntity.getClassName(), studentCount));
        }

        String oldData = String.format("{\"class_id\":%d,\"class_name\":\"%s\"}",
                classEntity.getClassId(), classEntity.getClassName());

        classRepository.delete(classEntity);
        log.info("Xóa lớp học thành công: {} (ID={})", classEntity.getClassName(), classId);

        loggingService.logDelete("classes", null, oldData);
    }

    /**
     * FR-19, FR-20: Phân công GVCN + Kiểm tra trùng
     */
    @Override
    public ClassDTO assignHomeroomTeacher(Integer classId, AssignTeacherRequest request) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Lớp học", "ID", classId));

        Integer yearId = classEntity.getAcademicYear().getYearId();
        User teacher = findAndValidateTeacher(request.getTeacherId(), yearId, classId);

        String oldTeacher = classEntity.getHomeroomTeacher() != null
                ? classEntity.getHomeroomTeacher().getFullName() : "Chưa phân công";

        classEntity.setHomeroomTeacher(teacher);
        ClassEntity updated = classRepository.save(classEntity);

        log.info("Phân công GVCN thành công: Lớp {} - GV {}",
                classEntity.getClassName(), teacher.getFullName());

        loggingService.logUpdate("classes", null,
                String.format("{\"homeroom_teacher\":\"%s\"}", oldTeacher),
                String.format("{\"homeroom_teacher\":\"%s\"}", teacher.getFullName()));

        return mapToDTO(updated);
    }

    /**
     * Xem chi tiết lớp học
     */
    @Override
    @Transactional(readOnly = true)
    public ClassDTO getClassById(Integer classId) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Lớp học", "ID", classId));
        return mapToDTO(classEntity);
    }

    /**
     * FR-21: Lọc danh sách lớp + phân trang
     */
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ClassDTO> filterClasses(String className, Integer gradeLevel,
                                                  Integer yearId, Integer teacherId,
                                                  int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("className").ascending());

        Page<ClassEntity> classPage = classRepository.filterClasses(
                className, gradeLevel, yearId, teacherId, pageable);

        List<ClassDTO> content = classPage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return PagedResponse.of(content, page, size,
                classPage.getTotalElements(), classPage.getTotalPages());
    }

    /**
     * FR-20: Tìm và validate giáo viên cho phân công GVCN
     * Kiểm tra: GV tồn tại, có vai trò teacher, không chủ nhiệm lớp khác cùng năm
     */
    private User findAndValidateTeacher(Integer teacherId, Integer yearId, Integer... excludeClassId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Giáo viên", "ID", teacherId));

        if (!"teacher".equals(teacher.getRole())) {
            throw new BusinessRuleException("Người dùng không phải giáo viên!");
        }

        // FR-20: Kiểm tra GV đã chủ nhiệm lớp nào trong cùng năm chưa
        Optional<ClassEntity> existingAssignment = classRepository
                .findByHomeroomTeacher_UserIdAndAcademicYear_YearId(teacherId, yearId);

        if (existingAssignment.isPresent()) {
            ClassEntity existing = existingAssignment.get();
            // Nếu đang update chính lớp đó thì OK
            if (excludeClassId.length > 0 && existing.getClassId().equals(excludeClassId[0])) {
                return teacher;
            }
            throw new BusinessRuleException(
                    String.format("Giáo viên %s đã chủ nhiệm lớp %s trong cùng năm học!",
                            teacher.getFullName(), existing.getClassName()));
        }

        return teacher;
    }

    /**
     * Chuyển đổi ClassEntity sang ClassDTO
     */
    private ClassDTO mapToDTO(ClassEntity entity) {
        long studentCount = studentClassRepository.countByClassId(entity.getClassId());

        return ClassDTO.builder()
                .classId(entity.getClassId())
                .className(entity.getClassName())
                .gradeLevel(entity.getGradeLevel())
                .yearName(entity.getAcademicYear() != null ? entity.getAcademicYear().getYearName() : null)
                .yearId(entity.getAcademicYear() != null ? entity.getAcademicYear().getYearId() : null)
                .homeroomTeacherName(entity.getHomeroomTeacher() != null
                        ? entity.getHomeroomTeacher().getFullName() : "Chưa phân công")
                .homeroomTeacherId(entity.getHomeroomTeacher() != null
                        ? entity.getHomeroomTeacher().getUserId() : null)
                .studentCount(studentCount)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
