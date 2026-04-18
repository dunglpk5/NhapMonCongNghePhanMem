package com.gtuschool.student.service;

import com.gtuschool.common.dto.PagedResponse;
import com.gtuschool.student.dto.CreateStudentRequest;
import com.gtuschool.student.dto.StudentDTO;
import com.gtuschool.student.dto.UpdateStudentRequest;

/**
 * Student Module Service Interface.
 * Theo thiết kế trong báo cáo: IStudentService
 *
 * FR-10: Thêm hồ sơ học sinh
 * FR-11: Tạo mã học sinh tự động
 * FR-12: Cập nhật hồ sơ học sinh
 * FR-13: Tìm kiếm học sinh
 * FR-14: Phân trang
 * FR-15: Lọc danh sách học sinh
 */
public interface IStudentService {

    /**
     * FR-10, FR-11: Thêm hồ sơ học sinh mới, tự động sinh mã HS
     *
     * @param request Thông tin học sinh cần tạo
     * @return StudentDTO chứa thông tin học sinh vừa tạo (bao gồm mã HS)
     */
    StudentDTO createStudent(CreateStudentRequest request);

    /**
     * FR-12: Cập nhật thông tin hồ sơ học sinh
     *
     * @param studentId ID học sinh cần cập nhật
     * @param request   Thông tin cần cập nhật
     * @return StudentDTO sau khi cập nhật
     */
    StudentDTO updateStudent(String studentId, UpdateStudentRequest request);

    /**
     * Xem chi tiết hồ sơ học sinh
     *
     * @param studentId ID học sinh
     * @return StudentDTO
     */
    StudentDTO getStudentById(String studentId);

    /**
     * FR-13, FR-14: Tìm kiếm học sinh theo từ khóa (mã HS, họ tên) + phân trang
     *
     * @param keyword Từ khóa tìm kiếm
     * @param page    Số trang (0-indexed)
     * @param size    Số bản ghi/trang (mặc định 20 theo FR-14)
     * @return PagedResponse chứa danh sách StudentDTO
     */
    PagedResponse<StudentDTO> searchStudents(String keyword, int page, int size);

    /**
     * FR-14, FR-15: Lọc danh sách học sinh theo nhiều tiêu chí + phân trang
     *
     * @param fullName  Họ tên (tùy chọn)
     * @param gender    Giới tính (tùy chọn)
     * @param address   Địa chỉ (tùy chọn)
     * @param ethnicity Dân tộc (tùy chọn)
     * @param religion  Tôn giáo (tùy chọn)
     * @param fatherName Họ tên cha (tùy chọn)
     * @param motherName Họ tên mẹ (tùy chọn)
     * @param phone     Số điện thoại (tùy chọn)
     * @param page      Số trang
     * @param size      Số bản ghi/trang
     * @return PagedResponse chứa danh sách StudentDTO
     */
    PagedResponse<StudentDTO> filterStudents(String fullName, Boolean gender, String address,
                                              String ethnicity, String religion,
                                              String fatherName, String motherName,
                                              String phone, int page, int size);

    /**
     * FR-11: Sinh mã học sinh tự động theo format HS + số thứ tự 6 chữ số
     *
     * @return Mã học sinh mới (ví dụ: HS000001)
     */
    String generateStudentId();
}
