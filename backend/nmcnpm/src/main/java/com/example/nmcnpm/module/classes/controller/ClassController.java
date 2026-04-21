package com.example.nmcnpm.module.classes.controller;

import com.example.nmcnpm.module.classes.entity.ClassEntity;
import com.example.nmcnpm.module.classes.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassController – API lấy danh sách lớp học.
 * Dùng cho dropdown chọn lớp trong các trang quản lý.
 */
@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClassController {

    private final ClassRepository classRepository;

    /**
     * GET /api/v1/classes – Lấy tất cả lớp học.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllClasses() {
        List<ClassEntity> classes = classRepository.findAll();

        List<Map<String, Object>> data = classes.stream().map(c -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("classId", c.getClassId());
            map.put("className", c.getClassName());
            map.put("gradeLevel", c.getGradeLevel());
            map.put("numberOfStudents", c.getNumberOfStudents());
            map.put("status", c.getStatus());
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
}
