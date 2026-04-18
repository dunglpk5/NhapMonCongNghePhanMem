package com.gtuschool.student.model;

import com.gtuschool.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho bảng students trong database.
 * FR-10: Thêm hồ sơ học sinh với đầy đủ thông tin cá nhân.
 * FR-11: Mã học sinh tự động sinh theo format 'HS' + số thứ tự.
 */
@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /**
     * Mã học sinh - Primary Key
     * Format: 'HS' + số thứ tự 6 chữ số (ví dụ: HS000001)
     * FR-11: Hệ thống tự động tạo mã học sinh duy nhất
     */
    @Id
    @Column(name = "student_id", length = 20)
    private String studentId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    /**
     * Giới tính: true = Nam, false = Nữ
     * (DB dùng kiểu BIT)
     */
    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "address")
    private String address;

    @Column(name = "ethnicity", length = 50)
    private String ethnicity;

    @Column(name = "religion", length = 50)
    private String religion;

    @Column(name = "father_name", length = 100)
    private String fatherName;

    @Column(name = "mother_name", length = 100)
    private String motherName;

    @Column(name = "phone", length = 15)
    private String phone;

    /**
     * Liên kết đến tài khoản user (nếu có)
     * FR-34: Học sinh có thể có tài khoản đăng nhập
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

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
