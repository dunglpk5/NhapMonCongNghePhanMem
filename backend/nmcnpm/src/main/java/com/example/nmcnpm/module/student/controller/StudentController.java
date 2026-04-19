package com.example.nmcnpm.module.student.controller;

import com.example.nmcnpm.module.student.dto.StudentDTO;
import com.example.nmcnpm.module.student.service.IStudentService;
import com.example.nmcnpm.module.validation.dto.ValidationResult;
import com.example.nmcnpm.module.validation.service.IValidationService;
import com.example.nmcnpm.shared.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Layer — Student Module.
 *
 * Trách nhiệm (theo tài liệu thiết kế):
 *  1. Nhận request từ frontend (btnLuu_click)
 *  2. Gọi Validation Module kiểm tra dữ liệu
 *  3. Gọi Student Service xử lý nghiệp vụ
 *  4. Trả ApiResponse về frontend
 *
 * KHÔNG chứa business logic — chỉ điều phối.
 */
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    // ── Thông báo MS (bảng MS — FR-10) ───────────────────────────────────
    private static final String MS_01 = "Vui lòng nhập đầy đủ thông tin bắt buộc!";
    private static final String MS_05 = "Thêm hồ sơ học sinh thất bại, vui lòng thử lại!";
    private static final String MS_06 = "Thêm hồ sơ học sinh thành công!";

    private final IValidationService validationService;
    private final IStudentService    studentService;

    public StudentController(IValidationService validationService,
                             IStudentService studentService) {
        this.validationService = validationService;
        this.studentService    = studentService;
    }

    /**
     * POST /api/v1/students
     *
     * Luồng xử lý (tương ứng Sequence Diagram FR-10):
     *  Bước 1: Nhận StudentDTO từ frontend
     *  Bước 2: validateRequired()
     *  Bước 3: validateFormat()
     *  Bước 4: themHoSoRQ() → Service xử lý tiếp (bước 5,6,7)
     *
     * @param dto         dữ liệu học sinh từ form
     * @param userDetails thông tin người dùng đang đăng nhập (JWT)
     */
    @PostMapping
    public ResponseEntity<ApiResponse<String>> themHoSoRQ(
            @RequestBody StudentDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Bước 2: Kiểm tra trường bắt buộc
        ValidationResult requiredCheck = validationService.validateRequired(dto);
        if (!requiredCheck.isValid()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(requiredCheck.getMessage()));
        }

        // Bước 3: Kiểm tra định dạng
        ValidationResult formatCheck = validationService.validateFormat(dto);
        if (!formatCheck.isValid()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(formatCheck.getMessage()));
        }

        // Bước 4–7: Giao cho Service xử lý nghiệp vụ
        try {
            Integer userId   = extractUserId(userDetails);
            String maHocSinh = studentService.themHoSo(dto, userId);

            return ResponseEntity.ok(ApiResponse.ok(MS_06, maHocSinh));

        } catch (StudentSaveException e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.fail(MS_05));
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    /**
     * Lấy userId từ JWT đã xác thực.
     * UserDetails.getUsername() trả về username; cần query DB để lấy id
     * hoặc dùng custom UserDetails chứa id — tùy implementation Auth Module.
     */
    private Integer extractUserId(UserDetails userDetails) {
        if (userDetails instanceof com.gtu.school.module.student.controller.GtuUserDetails gtu) {
            return gtu.getUserId();
        }
        return null;
    }
}
