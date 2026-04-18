package com.gtuschool.student.repository;

import com.gtuschool.student.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho bảng student_class.
 * Quản lý liên kết học sinh - lớp - năm học.
 */
@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, StudentClass.StudentClassId> {

    /**
     * Đếm số học sinh trong 1 lớp (dùng cho FR-18: kiểm tra trước khi xóa lớp)
     */
    long countByClassId(Integer classId);

    /**
     * Lấy danh sách học sinh theo lớp
     */
    List<StudentClass> findByClassId(Integer classId);

    /**
     * Lấy danh sách lớp của 1 học sinh
     */
    List<StudentClass> findByStudentId(String studentId);

    /**
     * Đếm số học sinh trong lớp theo năm học
     */
    long countByClassIdAndYearId(Integer classId, Integer yearId);

    /**
     * Tìm lớp hiện tại của học sinh (theo năm học)
     */
    @Query("SELECT sc FROM StudentClass sc WHERE sc.studentId = :studentId AND sc.yearId = :yearId")
    List<StudentClass> findByStudentIdAndYearId(@Param("studentId") String studentId,
                                                 @Param("yearId") Integer yearId);
}
