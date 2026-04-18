package com.gtuschool.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity đại diện cho bảng student_class trong database.
 * Bảng trung gian liên kết học sinh với lớp theo năm học.
 * Composite Primary Key: (student_id, class_id, year_id)
 */
@Entity
@Table(name = "student_class")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StudentClass.StudentClassId.class)
public class StudentClass {

    @Id
    @Column(name = "student_id", length = 20)
    private String studentId;

    @Id
    @Column(name = "class_id")
    private Integer classId;

    @Id
    @Column(name = "year_id")
    private Integer yearId;

    @Column(name = "enrolled_date")
    private LocalDate enrolledDate;

    @PrePersist
    protected void onCreate() {
        if (enrolledDate == null) {
            enrolledDate = LocalDate.now();
        }
    }

    /**
     * Composite Primary Key class
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentClassId implements Serializable {
        private String studentId;
        private Integer classId;
        private Integer yearId;
    }
}
