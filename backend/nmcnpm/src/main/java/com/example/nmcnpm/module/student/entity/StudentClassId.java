package com.example.nmcnpm.module.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentClassId implements Serializable {

    @Column(name = "student_id", length = 20, columnDefinition = "NVARCHAR(20)")
    private String studentId;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "year_id")
    private Integer yearId;

    public StudentClassId() {}

    public StudentClassId(String studentId, Integer classId, Integer yearId) {
        this.studentId = studentId;
        this.classId = classId;
        this.yearId = yearId;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public Integer getClassId() { return classId; }
    public void setClassId(Integer classId) { this.classId = classId; }
    public Integer getYearId() { return yearId; }
    public void setYearId(Integer yearId) { this.yearId = yearId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentClassId that = (StudentClassId) o;
        return Objects.equals(studentId, that.studentId) && 
               Objects.equals(classId, that.classId) && 
               Objects.equals(yearId, that.yearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, classId, yearId);
    }
}
