package com.example.nmcnpm.module.student.repository;

import com.example.nmcnpm.module.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository cho Student Module.
 * Chỉ được dùng nội bộ trong Student Module —
 * các module khác giao tiếp qua IStudentService.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    /**
     * Sinh số thứ tự tiếp theo theo từng năm.
     * Format theo thiết kế: HS + yyyy + 3 chữ số (ví dụ: HS2024001).
     */
    @Query(value = """
            SELECT ISNULL(
                MAX(CAST(RIGHT(student_id, 3) AS INT)),
            0) + 1
            FROM students
            WHERE student_id LIKE CONCAT('HS', :year, '%')
            """, nativeQuery = true)
    int getNextSequenceByYear(@Param("year") int year);

    /**
     * Tìm kiếm học sinh theo nhiều tiêu chí (FR-13, FR-15).
     * Phân trang 20 bản ghi / trang (FR-14).
     *
     * Tất cả điều kiện là tùy chọn — truyền null để bỏ qua.
     */
    @Query("""
            SELECT s FROM Student s
            WHERE (:maHocSinh IS NULL OR UPPER(s.studentId) LIKE UPPER(CONCAT('%', :maHocSinh, '%')))
              AND (:hoTen     IS NULL OR UPPER(s.fullName)   LIKE UPPER(CONCAT('%', :hoTen, '%')))
              AND (:classId   IS NULL OR EXISTS (SELECT 1 FROM StudentClass sc WHERE sc.student = s AND sc.classEntity.classId = :classId))
            """)
    Page<Student> search(
            @Param("maHocSinh") String maHocSinh,
            @Param("hoTen")     String hoTen,
            @Param("classId")   Integer classId,
            Pageable pageable
    );

    /**
     * Lọc theo nhiều tiêu chí (FR-15):
     * họ tên, ngày sinh, giới tính, địa chỉ, dân tộc, tôn giáo,
     * họ tên cha/mẹ, số điện thoại.
     */
    @Query("""
            SELECT s FROM Student s
            WHERE (:hoTen     IS NULL OR UPPER(s.fullName)   LIKE UPPER(CONCAT('%', :hoTen, '%')))
              AND (:danToc    IS NULL OR UPPER(s.ethnicity)   LIKE UPPER(CONCAT('%', :danToc, '%')))
              AND (:tonGiao   IS NULL OR UPPER(s.religion)    LIKE UPPER(CONCAT('%', :tonGiao, '%')))
              AND (:hoTenCha  IS NULL OR UPPER(s.fatherName)  LIKE UPPER(CONCAT('%', :hoTenCha, '%')))
              AND (:hoTenMe   IS NULL OR UPPER(s.motherName)  LIKE UPPER(CONCAT('%', :hoTenMe, '%')))
              AND (:sdt       IS NULL OR s.phone              LIKE CONCAT('%', :sdt, '%'))
              AND (:gender    IS NULL OR s.gender             = :gender)
            """)
    Page<Student> filter(
            @Param("hoTen")    String  hoTen,
            @Param("danToc")   String  danToc,
            @Param("tonGiao")  String  tonGiao,
            @Param("hoTenCha") String  hoTenCha,
            @Param("hoTenMe")  String  hoTenMe,
            @Param("sdt")      String  sdt,
            @Param("gender")   Boolean gender,
            Pageable pageable
    );
}
