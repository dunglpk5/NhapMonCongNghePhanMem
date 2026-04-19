package com.gtu.school.module.student.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * DTO cho thao tác thêm / cập nhật hồ sơ học sinh.
 * Tương ứng với input của btnLuu_click trong tài liệu thiết kế.
 */
public class StudentDTO {

    // ── Thông tin cá nhân ──────────────────────────────────────────────────

    @NotBlank
    private String hoTen;

    private LocalDate ngaySinh;

    private String gioiTinh;   // "Nam" | "Nữ" | "Khác"

    private String diaChi;

    private String danToc;

    private String tonGiao;

    // ── Thông tin liên lạc ─────────────────────────────────────────────────

    private String soDienThoai;

    // ── Thông tin phụ huynh ────────────────────────────────────────────────

    private String hoTenCha;

    private String hoTenMe;

    // ── Phân lớp ───────────────────────────────────────────────────────────

    private Integer classId;   // FK → Classes.class_id

    // ── Getters / Setters ──────────────────────────────────────────────────

    public String getHoTen()                  { return hoTen; }
    public void   setHoTen(String hoTen)      { this.hoTen = hoTen; }

    public LocalDate getNgaySinh()                    { return ngaySinh; }
    public void      setNgaySinh(LocalDate ngaySinh)  { this.ngaySinh = ngaySinh; }

    public String getGioiTinh()                     { return gioiTinh; }
    public void   setGioiTinh(String gioiTinh)      { this.gioiTinh = gioiTinh; }

    public String getDiaChi()                   { return diaChi; }
    public void   setDiaChi(String diaChi)      { this.diaChi = diaChi; }

    public String getDanToc()                   { return danToc; }
    public void   setDanToc(String danToc)      { this.danToc = danToc; }

    public String getTonGiao()                  { return tonGiao; }
    public void   setTonGiao(String tonGiao)    { this.tonGiao = tonGiao; }

    public String getSoDienThoai()                      { return soDienThoai; }
    public void   setSoDienThoai(String soDienThoai)    { this.soDienThoai = soDienThoai; }

    public String getHoTenCha()                     { return hoTenCha; }
    public void   setHoTenCha(String hoTenCha)      { this.hoTenCha = hoTenCha; }

    public String getHoTenMe()                  { return hoTenMe; }
    public void   setHoTenMe(String hoTenMe)    { this.hoTenMe = hoTenMe; }

    public Integer getClassId()                     { return classId; }
    public void    setClassId(Integer classId)      { this.classId = classId; }
}
