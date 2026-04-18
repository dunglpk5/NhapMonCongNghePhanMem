package com.gtuschool.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO trả về thông tin học sinh cho frontend.
 * Không expose trực tiếp entity để tách biệt lớp dữ liệu.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private String studentId;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;          // "Nam" hoặc "Nữ"
    private String address;
    private String ethnicity;
    private String religion;
    private String fatherName;
    private String motherName;
    private String phone;
    private String className;       // Tên lớp hiện tại (nếu có)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
