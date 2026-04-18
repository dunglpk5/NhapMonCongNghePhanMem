package com.gtuschool.classmodule.model;

import com.gtuschool.academicyear.model.AcademicYear;
import com.gtuschool.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho bảng classes trong database.
 * FR-16: Tạo lớp học mới.
 * FR-19: Phân công giáo viên chủ nhiệm.
 *
 * Lưu ý: Tên class là ClassEntity (không phải Class) để tránh trùng với java.lang.Class
 */
@Entity
@Table(name = "classes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "class_name", nullable = false, length = 20)
    private String className;

    /**
     * Khối lớp: 1-12 (THPT: 10, 11, 12)
     */
    @Column(name = "grade_level")
    private Integer gradeLevel;

    /**
     * Năm học - FK đến academic_years
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "year_id")
    private AcademicYear academicYear;

    /**
     * Giáo viên chủ nhiệm - FK đến users
     * FR-19: Phân công GVCN cho từng lớp
     * FR-20: 1 GV không chủ nhiệm nhiều lớp cùng năm (UNIQUE constraint)
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User homeroomTeacher;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
