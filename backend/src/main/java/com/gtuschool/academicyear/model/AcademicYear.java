package com.gtuschool.academicyear.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho bảng academic_years trong database.
 * FR-31 ~ FR-35: Quản lý năm học.
 */
@Entity
@Table(name = "academic_years")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "year_id")
    private Integer yearId;

    /**
     * Tên năm học, ví dụ: '2024-2025'
     */
    @Column(name = "year_name", nullable = false, unique = true, length = 20)
    private String yearName;

    /**
     * FR-34: Chỉ 1 năm học hiện tại tại 1 thời điểm
     */
    @Column(name = "is_current")
    private Boolean isCurrent;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
