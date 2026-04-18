package com.gtuschool.classmodule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO cho thao tác tạo lớp học mới.
 * FR-16: Tạo lớp học với tên lớp, khối, năm học, sĩ số, trạng thái.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassRequest {

    @NotBlank(message = "Vui lòng nhập tên lớp!")
    private String className;

    @NotNull(message = "Vui lòng chọn khối!")
    private Integer gradeLevel;

    @NotNull(message = "Vui lòng chọn năm học!")
    private Integer yearId;

    /** ID giáo viên chủ nhiệm (không bắt buộc khi tạo lớp) */
    private Integer homeroomTeacherId;
}
