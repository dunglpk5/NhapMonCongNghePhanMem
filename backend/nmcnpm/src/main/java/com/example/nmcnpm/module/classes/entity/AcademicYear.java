package com.example.nmcnpm.module.classes.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "academic_years")
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "year_id")
    private Integer yearId;

    @Column(name = "year_name", nullable = false, unique = true, length = 20, columnDefinition = "NVARCHAR(20)")
    private String yearName;

    @Column(name = "is_current")
    private Boolean isCurrent;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (isCurrent == null) isCurrent = false;
    }

    public Integer getYearId() { return yearId; }
    public void setYearId(Integer yearId) { this.yearId = yearId; }
    public String getYearName() { return yearName; }
    public void setYearName(String yearName) { this.yearName = yearName; }
    public Boolean getIsCurrent() { return isCurrent; }
    public void setIsCurrent(Boolean isCurrent) { this.isCurrent = isCurrent; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
