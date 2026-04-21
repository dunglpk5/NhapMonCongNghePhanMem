package com.example.nmcnpm.module.student.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

/**
 * Entity ánh xạ bảng [students] trong SchoolManagementSystem.
 *
 * Lưu ý quan trọng:
 *  - student_id KHÔNG dùng @GeneratedValue — được sinh bởi stored procedure
 *    hoặc service theo format HS + năm + STT (ví dụ: HS2024001)
 *  - gender là BIT trong DB → Boolean trong Java (true = Nam, false = Nữ)
 *  - user_id là FK → users(user_id), chỉ lưu Integer để tránh circular dependency
 *  - created_at / updated_at được DB tự quản lý qua trigger
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "student_id", nullable = false, length = 20, columnDefinition = "NVARCHAR(20)")
    private String studentId;   // HS000001, HS000002, ...

    @Column(name = "full_name", nullable = false, length = 100, columnDefinition = "NVARCHAR(100)")
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    /**
     * true  = Nam
     * false = Nữ
     * DB: BIT column
     */
    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "address", length = 255)
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

    @Column(name = "status", length = 50, columnDefinition = "NVARCHAR(50)")
    private String status;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentClass> studentClasses = new HashSet<>();

    /**
     * FK → users(user_id).
     * Lưu dạng Integer thay vì @ManyToOne để giữ Student Module độc lập.
     * Null nếu học sinh chưa có tài khoản đăng nhập.
     */
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ── Lifecycle ─────────────────────────────────────────────────────────

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ── Getters / Setters ─────────────────────────────────────────────────

    public String getStudentId()                        { return studentId; }
    public void   setStudentId(String studentId)        { this.studentId = studentId; }

    public String getFullName()                         { return fullName; }
    public void   setFullName(String fullName)          { this.fullName = fullName; }

    public LocalDate getDateOfBirth()                   { return dateOfBirth; }
    public void      setDateOfBirth(LocalDate d)        { this.dateOfBirth = d; }

    public Boolean getGender()                          { return gender; }
    public void    setGender(Boolean gender)            { this.gender = gender; }

    public String getAddress()                          { return address; }
    public void   setAddress(String address)            { this.address = address; }

    public String getEthnicity()                        { return ethnicity; }
    public void   setEthnicity(String ethnicity)        { this.ethnicity = ethnicity; }

    public String getReligion()                         { return religion; }
    public void   setReligion(String religion)          { this.religion = religion; }

    public String getFatherName()                       { return fatherName; }
    public void   setFatherName(String fatherName)      { this.fatherName = fatherName; }

    public String getMotherName()                       { return motherName; }
    public void   setMotherName(String motherName)      { this.motherName = motherName; }

    public String getPhone()                            { return phone; }
    public void   setPhone(String phone)                { this.phone = phone; }

    public String getStatus()                           { return status; }
    public void   setStatus(String status)              { this.status = status; }

    public Integer getUserId()                          { return userId; }
    public void    setUserId(Integer userId)            { this.userId = userId; }

    public Set<StudentClass> getStudentClasses()        { return studentClasses; }
    public void setStudentClasses(Set<StudentClass> sc) { this.studentClasses = sc; }

    public LocalDateTime getCreatedAt()                 { return createdAt; }
    public LocalDateTime getUpdatedAt()                 { return updatedAt; }
}
