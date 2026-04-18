package com.gtuschool.classmodule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO cho thao tác phân công GVCN.
 * FR-19: Phân công giáo viên chủ nhiệm cho từng lớp.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignTeacherRequest {

    @NotNull(message = "Vui lòng chọn giáo viên chủ nhiệm!")
    private Integer teacherId;
}
