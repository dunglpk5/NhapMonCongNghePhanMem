package com.gtuschool.classmodule.repository;

import com.gtuschool.classmodule.model.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng classes.
 * FR-16~FR-21: CRUD lớp học, phân công GVCN, lọc danh sách.
 */
@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {

    /**
     * FR-16: Kiểm tra trùng lớp (cùng tên + khối + năm học)
     */
    Optional<ClassEntity> findByClassNameAndGradeLevelAndAcademicYear_YearId(
            String className, Integer gradeLevel, Integer yearId);

    /**
     * FR-20: Kiểm tra giáo viên đã chủ nhiệm lớp nào trong cùng năm học chưa
     */
    Optional<ClassEntity> findByHomeroomTeacher_UserIdAndAcademicYear_YearId(
            Integer teacherUserId, Integer yearId);

    /**
     * Lấy danh sách lớp theo năm học
     */
    List<ClassEntity> findByAcademicYear_YearId(Integer yearId);

    /**
     * Lấy danh sách lớp theo khối
     */
    List<ClassEntity> findByGradeLevel(Integer gradeLevel);

    /**
     * FR-21: Lọc nâng cao - kết hợp nhiều tiêu chí
     */
    @Query("SELECT c FROM ClassEntity c " +
           "LEFT JOIN c.academicYear ay " +
           "LEFT JOIN c.homeroomTeacher t WHERE " +
           "(:className IS NULL OR c.className LIKE CONCAT('%', :className, '%')) AND " +
           "(:gradeLevel IS NULL OR c.gradeLevel = :gradeLevel) AND " +
           "(:yearId IS NULL OR ay.yearId = :yearId) AND " +
           "(:teacherId IS NULL OR t.userId = :teacherId)")
    Page<ClassEntity> filterClasses(
            @Param("className") String className,
            @Param("gradeLevel") Integer gradeLevel,
            @Param("yearId") Integer yearId,
            @Param("teacherId") Integer teacherId,
            Pageable pageable
    );

    /**
     * Đếm lớp theo năm học
     */
    long countByAcademicYear_YearId(Integer yearId);

    /**
     * Đếm lớp theo khối và năm học
     */
    long countByGradeLevelAndAcademicYear_YearId(Integer gradeLevel, Integer yearId);
}
