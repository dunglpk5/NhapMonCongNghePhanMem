package com.gtuschool.classmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO trả về thông tin lớp học cho frontend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {

    private Integer classId;
    private String className;
    private Integer gradeLevel;
    private String yearName;          // Tên năm học (ví dụ: 2025-2026)
    private Integer yearId;
    private String homeroomTeacherName; // Tên GVCN
    private Integer homeroomTeacherId;
    private long studentCount;        // Sĩ số
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
