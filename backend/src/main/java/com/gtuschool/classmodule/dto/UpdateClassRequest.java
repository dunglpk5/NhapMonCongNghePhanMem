package com.gtuschool.classmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO cho thao tác cập nhật lớp học.
 * FR-17: Cập nhật thông tin lớp học.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClassRequest {

    private String className;
    private Integer gradeLevel;
    private Integer yearId;
    private Integer homeroomTeacherId;
}
