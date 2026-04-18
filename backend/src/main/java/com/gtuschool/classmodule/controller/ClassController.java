package com.gtuschool.classmodule.controller;

import com.gtuschool.classmodule.dto.AssignTeacherRequest;
import com.gtuschool.classmodule.dto.ClassDTO;
import com.gtuschool.classmodule.dto.CreateClassRequest;
import com.gtuschool.classmodule.dto.UpdateClassRequest;
import com.gtuschool.classmodule.service.IClassService;
import com.gtuschool.common.dto.ApiResponse;
import com.gtuschool.common.dto.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class Module Controller (REST API Layer).
 * Theo thiết kế trong báo cáo: ClassController
 *
 * Endpoints:
 * POST   /api/classes                          → FR-16: Tạo lớp học
 * GET    /api/classes/{id}                     → Xem chi tiết
 * PUT    /api/classes/{id}                     → FR-17: Cập nhật lớp
 * DELETE /api/classes/{id}                     → FR-18: Xóa lớp
 * PUT    /api/classes/{id}/homeroom-teacher     → FR-19: Phân công GVCN
 * GET    /api/classes                          → FR-21: Danh sách + lọc
 */
@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@Slf4j
public class ClassController {

    private final IClassService classService;

    /**
     * FR-16: Tạo lớp học mới
     * POST /api/classes
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ClassDTO>> createClass(
            @Valid @RequestBody CreateClassRequest request) {
        log.info("POST /api/classes - Tạo lớp: {}", request.getClassName());
        ClassDTO created = classService.createClass(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo lớp học thành công!", created));
    }

    /**
     * Xem chi tiết lớp học
     * GET /api/classes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassDTO>> getClass(@PathVariable("id") Integer id) {
        log.info("GET /api/classes/{} - Xem chi tiết", id);
        ClassDTO classDTO = classService.getClassById(id);
        return ResponseEntity.ok(ApiResponse.success("Thành công", classDTO));
    }

    /**
     * FR-17: Cập nhật thông tin lớp
     * PUT /api/classes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassDTO>> updateClass(
            @PathVariable("id") Integer id,
            @RequestBody UpdateClassRequest request) {
        log.info("PUT /api/classes/{} - Cập nhật lớp", id);
        ClassDTO updated = classService.updateClass(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật lớp học thành công!", updated));
    }

    /**
     * FR-18: Xóa lớp (chỉ khi 0 học sinh)
     * DELETE /api/classes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClass(@PathVariable("id") Integer id) {
        log.info("DELETE /api/classes/{} - Xóa lớp", id);
        classService.deleteClass(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa lớp học thành công!"));
    }

    /**
     * FR-19: Phân công giáo viên chủ nhiệm
     * FR-20: Kiểm tra trùng GVCN (tích hợp trong service)
     * PUT /api/classes/{id}/homeroom-teacher
     */
    @PutMapping("/{id}/homeroom-teacher")
    public ResponseEntity<ApiResponse<ClassDTO>> assignHomeroomTeacher(
            @PathVariable("id") Integer id,
            @Valid @RequestBody AssignTeacherRequest request) {
        log.info("PUT /api/classes/{}/homeroom-teacher - Phân công GVCN: teacherId={}",
                id, request.getTeacherId());
        ClassDTO updated = classService.assignHomeroomTeacher(id, request);
        return ResponseEntity.ok(ApiResponse.success("Phân công giáo viên chủ nhiệm thành công!", updated));
    }

    /**
     * FR-21: Lọc danh sách lớp học + phân trang
     * GET /api/classes?className=...&gradeLevel=10&yearId=1&teacherId=2&page=0&size=20
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ClassDTO>>> filterClasses(
            @RequestParam(value = "className", required = false) String className,
            @RequestParam(value = "gradeLevel", required = false) Integer gradeLevel,
            @RequestParam(value = "yearId", required = false) Integer yearId,
            @RequestParam(value = "teacherId", required = false) Integer teacherId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        log.info("GET /api/classes - Lọc: grade={}, year={}, page={}", gradeLevel, yearId, page);
        PagedResponse<ClassDTO> result = classService.filterClasses(
                className, gradeLevel, yearId, teacherId, page, size);
        return ResponseEntity.ok(ApiResponse.success("Thành công", result));
    }
}
