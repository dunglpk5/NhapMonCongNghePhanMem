package com.example.nmcnpm.module.student.controller;

import com.example.nmcnpm.module.student.dto.StudentDTO;
import com.example.nmcnpm.module.student.dto.StudentresponseDTO;
import com.example.nmcnpm.module.student.service.IStudentService;
import com.example.nmcnpm.module.validation.dto.ValidationResult;
import com.example.nmcnpm.module.validation.service.IValidationService;
import com.example.nmcnpm.module.core.response.ApiResponse;
import com.example.nmcnpm.module.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * StudentController – REST controller cho Student Module.
 *
 * Tương ứng với:
 *   FR-10: Thêm hồ sơ học sinh
 *   FR-12: Cập nhật hồ sơ học sinh
 *   FR-13: Tìm kiếm học sinh
 *   FR-14: Hiển thị phân trang
 *   FR-15: Lọc danh sách học sinh
 *   FR-36: Xem hồ sơ cá nhân
 *
 * Base path: /api/v1/students
 */
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private static final String MS_05 = "Thêm hồ sơ học sinh thất bại, vui lòng thử lại!";
    private static final String MS_06 = "Thêm hồ sơ học sinh thành công!";

    private final IValidationService validationService;
    private final IStudentService studentService;

    public StudentController(IValidationService validationService,
                             IStudentService studentService) {
        this.validationService = validationService;
        this.studentService = studentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICE_STAFF')")
    public ResponseEntity<ApiResponse<String>> themHoSoRQ(
            @RequestBody StudentDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        ValidationResult requiredCheck = validationService.validateRequired(dto);
        if (!requiredCheck.isValid()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(requiredCheck.getMessage()));
        }

        ValidationResult formatCheck = validationService.validateFormat(dto);
        if (!formatCheck.isValid()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(formatCheck.getMessage()));
        }

        try {
            Integer userId = extractUserId(userDetails);
            String maHocSinh = studentService.themHoSo(dto, userId);
            return ResponseEntity.ok(ApiResponse.ok(MS_06, maHocSinh));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.fail(MS_05));
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICE_STAFF', 'PRINCIPAL')")
    public ResponseEntity<ApiResponse<Page<StudentresponseDTO>>> danhSach(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fullName").ascending());
        Page<StudentresponseDTO> result = studentService.timKiem(null, null, null, pageable);
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách thành công", result));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICE_STAFF', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<ApiResponse<Page<StudentresponseDTO>>> timKiem(
            @RequestParam(required = false) String maHocSinh,
            @RequestParam(required = false) String hoTen,
            @RequestParam(required = false) Integer classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        ValidationResult check = validationService.validateSearchInput(maHocSinh, hoTen, classId);
        if (!check.isValid()) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(check.getMessage()));
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<StudentresponseDTO> result = studentService.timKiem(maHocSinh, hoTen, classId, pageable);

        if (result.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.fail("Không tìm thấy học sinh phù hợp!"));
        }
        return ResponseEntity.ok(ApiResponse.ok("Tìm kiếm thành công", result));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICE_STAFF', 'PRINCIPAL')")
    public ResponseEntity<ApiResponse<Page<StudentresponseDTO>>> locDanhSach(
            @RequestParam(required = false) String hoTen,
            @RequestParam(required = false) String danToc,
            @RequestParam(required = false) String tonGiao,
            @RequestParam(required = false) String hoTenCha,
            @RequestParam(required = false) String hoTenMe,
            @RequestParam(required = false) String sdt,
            @RequestParam(required = false) Boolean gender,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fullName").ascending());
        Page<StudentresponseDTO> result = studentService.locDanhSach(
                hoTen, danToc, tonGiao, hoTenCha, hoTenMe, sdt, gender, pageable);

        return ResponseEntity.ok(ApiResponse.ok("Lọc thành công", result));
    }

    @GetMapping("/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICE_STAFF', 'PRINCIPAL', 'TEACHER', 'STUDENT')")
    public ResponseEntity<ApiResponse<StudentresponseDTO>> xemChiTiet(
            @PathVariable String studentId) {

        StudentresponseDTO dto = studentService.xemChiTiet(studentId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin thành công", dto));
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICE_STAFF')")
    public ResponseEntity<ApiResponse<Void>> capNhatHoSo(
            @PathVariable String studentId,
            @RequestBody StudentDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        ValidationResult requiredCheck = validationService.validateRequired(dto);
        if (!requiredCheck.isValid()) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(requiredCheck.getMessage()));
        }

        ValidationResult formatCheck = validationService.validateFormat(dto);
        if (!formatCheck.isValid()) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(formatCheck.getMessage()));
        }

        try {
            Integer userId = extractUserId(userDetails);
            studentService.capNhatHoSo(studentId, dto, userId);
            return ResponseEntity.ok(ApiResponse.ok("Cập nhật hồ sơ thành công"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.fail("Cập nhật thất bại, vui lòng thử lại!"));
        }
    }

    /**
     * Trích xuất userId từ Spring Security principal.
     * Principal là User entity (implements UserDetails).
     */
    private Integer extractUserId(UserDetails userDetails) {
        if (userDetails instanceof User) {
            return ((User) userDetails).getUserId();
        }
        return null;
    }
}
