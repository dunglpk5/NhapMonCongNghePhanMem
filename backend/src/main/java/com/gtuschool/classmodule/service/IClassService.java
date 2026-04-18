package com.gtuschool.classmodule.service;

import com.gtuschool.classmodule.dto.AssignTeacherRequest;
import com.gtuschool.classmodule.dto.ClassDTO;
import com.gtuschool.classmodule.dto.CreateClassRequest;
import com.gtuschool.classmodule.dto.UpdateClassRequest;
import com.gtuschool.common.dto.PagedResponse;

/**
 * Class Module Service Interface.
 * Theo thiết kế trong báo cáo: IClassService
 *
 * FR-16: Tạo lớp học
 * FR-17: Cập nhật lớp
 * FR-18: Xóa lớp (khi 0 HS)
 * FR-19: Phân công GVCN
 * FR-20: Kiểm tra trùng GVCN
 * FR-21: Lọc danh sách lớp
 */
public interface IClassService {

    /**
     * FR-16: Tạo lớp học mới
     */
    ClassDTO createClass(CreateClassRequest request);

    /**
     * FR-17: Cập nhật thông tin lớp
     */
    ClassDTO updateClass(Integer classId, UpdateClassRequest request);

    /**
     * FR-18: Xóa lớp (chỉ khi 0 học sinh)
     */
    void deleteClass(Integer classId);

    /**
     * FR-19: Phân công giáo viên chủ nhiệm
     * FR-20: Kiểm tra trùng GVCN (tích hợp)
     */
    ClassDTO assignHomeroomTeacher(Integer classId, AssignTeacherRequest request);

    /**
     * Xem chi tiết lớp học
     */
    ClassDTO getClassById(Integer classId);

    /**
     * FR-21: Lọc danh sách lớp + phân trang
     */
    PagedResponse<ClassDTO> filterClasses(String className, Integer gradeLevel,
                                           Integer yearId, Integer teacherId,
                                           int page, int size);
}
