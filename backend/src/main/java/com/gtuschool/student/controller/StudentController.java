package com.gtuschool.student.controller;

import com.gtuschool.common.dto.ApiResponse;
import com.gtuschool.common.dto.PagedResponse;
import com.gtuschool.student.dto.CreateStudentRequest;
import com.gtuschool.student.dto.StudentDTO;
import com.gtuschool.student.dto.UpdateStudentRequest;
import com.gtuschool.student.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Student Module Controller (REST API Layer).
 * Theo thiết kế trong báo cáo: StudentController
 *
 * Vai trò:
 * - Nhận HTTP request từ frontend
 * - Validate dữ liệu cơ bản (format, null, type)
 * - Gọi Service để xử lý nghiệp vụ
 * - Trả HTTP response với status code phù hợp
 *
 * Endpoints:
 * POST   /api/students              → FR-10: Thêm hồ sơ học sinh
 * GET    /api/students/{id}         → Xem chi tiết
 * PUT    /api/students/{id}         → FR-12: Cập nhật hồ sơ
 * GET    /api/students              → FR-14, FR-15: Danh sách + lọc + phân trang
 * GET    /api/students/search       → FR-13: Tìm kiếm
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final IStudentService studentService;

    /**
     * FR-10: Thêm hồ sơ học sinh mới
     * POST /api/students
     *
     * @param request Thông tin học sinh (validated bởi @Valid)
     * @return StudentDTO với mã HS tự động sinh (FR-11)
     */
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(
            @Valid @RequestBody CreateStudentRequest request) {
        log.info("POST /api/students - Thêm hồ sơ học sinh: {}", request.getFullName());
        StudentDTO created = studentService.createStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Thêm hồ sơ học sinh thành công!", created));
    }

    /**
     * Xem chi tiết hồ sơ học sinh
     * GET /api/students/{id}
     *
     * @param id Mã học sinh (ví dụ: HS000001)
     * @return StudentDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudent(@PathVariable("id") String id) {
        log.info("GET /api/students/{} - Xem chi tiết", id);
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success("Thành công", student));
    }

    /**
     * FR-12: Cập nhật hồ sơ học sinh
     * PUT /api/students/{id}
     *
     * @param id      Mã học sinh
     * @param request Thông tin cần cập nhật (partial update)
     * @return StudentDTO sau cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(
            @PathVariable("id") String id,
            @RequestBody UpdateStudentRequest request) {
        log.info("PUT /api/students/{} - Cập nhật hồ sơ", id);
        StudentDTO updated = studentService.updateStudent(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật hồ sơ học sinh thành công!", updated));
    }

    /**
     * FR-13, FR-14: Tìm kiếm học sinh theo từ khóa + phân trang
     * GET /api/students/search?keyword=...&page=0&size=20
     *
     * @param keyword Từ khóa (mã HS hoặc họ tên)
     * @param page    Số trang (mặc định 0)
     * @param size    Số bản ghi/trang (mặc định 20 - FR-14)
     * @return Danh sách StudentDTO phân trang
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<StudentDTO>>> searchStudents(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        log.info("GET /api/students/search - keyword={}, page={}, size={}", keyword, page, size);
        PagedResponse<StudentDTO> result = studentService.searchStudents(keyword, page, size);
        return ResponseEntity.ok(ApiResponse.success("Thành công", result));
    }

    /**
     * FR-14, FR-15: Lọc danh sách học sinh theo nhiều tiêu chí + phân trang
     * GET /api/students?fullName=...&gender=true&ethnicity=...&page=0&size=20
     *
     * @param fullName   Họ tên (tùy chọn)
     * @param gender     Giới tính: true=Nam, false=Nữ (tùy chọn)
     * @param address    Địa chỉ (tùy chọn)
     * @param ethnicity  Dân tộc (tùy chọn)
     * @param religion   Tôn giáo (tùy chọn)
     * @param fatherName Họ tên cha (tùy chọn)
     * @param motherName Họ tên mẹ (tùy chọn)
     * @param phone      Số điện thoại (tùy chọn)
     * @param page       Số trang (mặc định 0)
     * @param size       Số bản ghi/trang (mặc định 20)
     * @return Danh sách StudentDTO phân trang
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<StudentDTO>>> filterStudents(
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "gender", required = false) Boolean gender,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "ethnicity", required = false) String ethnicity,
            @RequestParam(value = "religion", required = false) String religion,
            @RequestParam(value = "fatherName", required = false) String fatherName,
            @RequestParam(value = "motherName", required = false) String motherName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        log.info("GET /api/students - Lọc danh sách, page={}, size={}", page, size);
        PagedResponse<StudentDTO> result = studentService.filterStudents(
                fullName, gender, address, ethnicity, religion,
                fatherName, motherName, phone, page, size);
        return ResponseEntity.ok(ApiResponse.success("Thành công", result));
    }
}
