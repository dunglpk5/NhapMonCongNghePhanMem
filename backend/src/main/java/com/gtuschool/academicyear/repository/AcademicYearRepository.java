package com.gtuschool.academicyear.repository;

import com.gtuschool.academicyear.model.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng academic_years.
 * FR-34: Lấy năm học hiện tại.
 * FR-35: Gán giá trị năm học mặc định.
 */
@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Integer> {

    /**
     * FR-34: Tìm năm học hiện tại (is_current = true)
     */
    Optional<AcademicYear> findByIsCurrentTrue();

    Optional<AcademicYear> findByYearName(String yearName);
}
