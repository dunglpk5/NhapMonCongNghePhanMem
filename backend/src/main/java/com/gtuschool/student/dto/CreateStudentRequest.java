package com.gtuschool.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request DTO cho thao tác thêm mới học sinh.
 * FR-10: Thêm hồ sơ học sinh với đầy đủ thông tin bắt buộc.
 * NFR-18: Label rõ ràng, validate đầu vào.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequest {

    @NotBlank(message = "Vui lòng nhập họ tên học sinh!")
    private String fullName;

    @NotNull(message = "Vui lòng chọn ngày sinh!")
    private LocalDate dateOfBirth;

    @NotNull(message = "Vui lòng chọn giới tính!")
    private Boolean gender;  // true = Nam, false = Nữ

    @NotBlank(message = "Vui lòng nhập địa chỉ!")
    private String address;

    @NotBlank(message = "Vui lòng nhập dân tộc!")
    private String ethnicity;

    @NotBlank(message = "Vui lòng nhập tôn giáo!")
    private String religion;

    @NotBlank(message = "Vui lòng nhập họ tên cha!")
    private String fatherName;

    @NotBlank(message = "Vui lòng nhập họ tên mẹ!")
    private String motherName;

    @NotBlank(message = "Vui lòng nhập số điện thoại liên hệ!")
    private String phone;
}
