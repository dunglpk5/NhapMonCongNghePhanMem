package com.example.nmcnpm.module.student.dto;

import com.example.nmcnpm.module.student.entity.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO trả về cho frontend khi xem / tìm kiếm hồ sơ học sinh.
 */
public class StudentresponseDTO {

    private String studentId;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String danToc;
    private String tonGiao;
    private String hoTenCha;
    private String hoTenMe;
    private String soDienThoai;
    private Integer classId;
    private String className;
    private String trangThai;
    private LocalDateTime createdAt;

    public static StudentresponseDTO from(Student s) {
        StudentresponseDTO dto = new StudentresponseDTO();
        dto.studentId = s.getStudentId();
        dto.hoTen = s.getFullName();
        dto.ngaySinh = s.getDateOfBirth();
        dto.gioiTinh = Boolean.TRUE.equals(s.getGender()) ? "Nam" : "Nữ";
        dto.diaChi = s.getAddress();
        dto.danToc = s.getEthnicity();
        dto.tonGiao = s.getReligion();
        dto.hoTenCha = s.getFatherName();
        dto.hoTenMe = s.getMotherName();
        dto.soDienThoai = s.getPhone();
        if (s.getStudentClasses() != null && !s.getStudentClasses().isEmpty()) {
            var activeClassOpt = s.getStudentClasses().stream()
                .filter(sc -> Boolean.TRUE.equals(sc.getAcademicYear().getIsCurrent()))
                .findFirst();
            if (activeClassOpt.isPresent()) {
                dto.classId = activeClassOpt.get().getClassEntity().getClassId();
                dto.className = activeClassOpt.get().getClassEntity().getClassName();
            } else {
                var firstSc = s.getStudentClasses().iterator().next();
                dto.classId = firstSc.getClassEntity().getClassId();
                dto.className = firstSc.getClassEntity().getClassName();
            }
        }
        dto.createdAt = s.getCreatedAt();
        dto.trangThai = s.getStatus() != null ? s.getStatus() : "Đang học";
        return dto;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getDanToc() { return danToc; }
    public void setDanToc(String danToc) { this.danToc = danToc; }
    public String getTonGiao() { return tonGiao; }
    public void setTonGiao(String tonGiao) { this.tonGiao = tonGiao; }
    public String getHoTenCha() { return hoTenCha; }
    public void setHoTenCha(String hoTenCha) { this.hoTenCha = hoTenCha; }
    public String getHoTenMe() { return hoTenMe; }
    public void setHoTenMe(String hoTenMe) { this.hoTenMe = hoTenMe; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public Integer getClassId() { return classId; }
    public void setClassId(Integer classId) { this.classId = classId; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
