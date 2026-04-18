package com.gtuschool.student.repository;

import com.gtuschool.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng students.
 * FR-13: Tìm kiếm học sinh theo mã, họ tên, lớp.
 * FR-14: Phân trang (20/trang).
 * FR-15: Lọc danh sách theo nhiều tiêu chí.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    /**
     * FR-11: Lấy mã học sinh lớn nhất để sinh mã tiếp theo
     */
    @Query("SELECT MAX(s.studentId) FROM Student s")
    Optional<String> findMaxStudentId();

    /**
     * FR-13: Tìm kiếm theo từ khóa (mã HS hoặc họ tên)
     */
    @Query("SELECT s FROM Student s WHERE " +
           "s.studentId LIKE CONCAT('%', :keyword, '%') OR " +
           "s.fullName LIKE CONCAT('%', :keyword, '%')")
    Page<Student> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * FR-15: Lọc theo họ tên (LIKE)
     */
    Page<Student> findByFullNameContaining(String fullName, Pageable pageable);

    /**
     * FR-15: Lọc theo giới tính
     */
    Page<Student> findByGender(Boolean gender, Pageable pageable);

    /**
     * FR-15: Lọc theo dân tộc
     */
    Page<Student> findByEthnicity(String ethnicity, Pageable pageable);

    /**
     * FR-15: Lọc theo tôn giáo
     */
    Page<Student> findByReligion(String religion, Pageable pageable);

    /**
     * FR-15: Lọc nâng cao - kết hợp nhiều tiêu chí
     */
    @Query("SELECT s FROM Student s WHERE " +
           "(:fullName IS NULL OR s.fullName LIKE CONCAT('%', :fullName, '%')) AND " +
           "(:gender IS NULL OR s.gender = :gender) AND " +
           "(:address IS NULL OR s.address LIKE CONCAT('%', :address, '%')) AND " +
           "(:ethnicity IS NULL OR s.ethnicity = :ethnicity) AND " +
           "(:religion IS NULL OR s.religion = :religion) AND " +
           "(:fatherName IS NULL OR s.fatherName LIKE CONCAT('%', :fatherName, '%')) AND " +
           "(:motherName IS NULL OR s.motherName LIKE CONCAT('%', :motherName, '%')) AND " +
           "(:phone IS NULL OR s.phone LIKE CONCAT('%', :phone, '%'))")
    Page<Student> filterStudents(
            @Param("fullName") String fullName,
            @Param("gender") Boolean gender,
            @Param("address") String address,
            @Param("ethnicity") String ethnicity,
            @Param("religion") String religion,
            @Param("fatherName") String fatherName,
            @Param("motherName") String motherName,
            @Param("phone") String phone,
            Pageable pageable
    );
}
