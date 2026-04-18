package com.gtuschool.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request DTO cho thao tác cập nhật hồ sơ học sinh.
 * FR-12: Cập nhật thông tin hồ sơ học sinh.
 * Tất cả field là optional - chỉ cập nhật field được gửi lên.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentRequest {

    private String fullName;
    private LocalDate dateOfBirth;
    private Boolean gender;
    private String address;
    private String ethnicity;
    private String religion;
    private String fatherName;
    private String motherName;
    private String phone;
}
