package com.example.nmcnpm.module.student.entity;

import com.example.nmcnpm.module.classes.entity.ClassEntity;
import com.example.nmcnpm.module.classes.entity.AcademicYear;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student_class")
public class StudentClass {

    @EmbeddedId
    private StudentClassId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("classId")
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("yearId")
    @JoinColumn(name = "year_id")
    private AcademicYear academicYear;

    @Column(name = "enrolled_date")
    private LocalDate enrolledDate;

    @PrePersist
    public void prePersist() {
        if (enrolledDate == null) enrolledDate = LocalDate.now();
    }

    public StudentClassId getId() { return id; }
    public void setId(StudentClassId id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public ClassEntity getClassEntity() { return classEntity; }
    public void setClassEntity(ClassEntity classEntity) { this.classEntity = classEntity; }
    public AcademicYear getAcademicYear() { return academicYear; }
    public void setAcademicYear(AcademicYear academicYear) { this.academicYear = academicYear; }
    public LocalDate getEnrolledDate() { return enrolledDate; }
    public void setEnrolledDate(LocalDate enrolledDate) { this.enrolledDate = enrolledDate; }
}
