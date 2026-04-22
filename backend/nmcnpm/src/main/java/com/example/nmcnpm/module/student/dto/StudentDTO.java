package com.example.nmcnpm.module.student.dto;

import java.time.LocalDate;

/**
 * DTO nhận dữ liệu từ frontend khi thêm / cập nhật hồ sơ học sinh.
 * Tương ứng với input của btnLuu_click trong tài liệu thiết kế FR-10.
 *
 * KHÔNG dùng annotations javax.validation ở đây —
 * validation được xử lý bởi Validation Module (IValidationService).
 */
public class StudentDTO {

    // ── Thông tin cá nhân ─────────────────────────────────────────────────
    private String    hoTen;         // txtHoTen        — bắt buộc
    private LocalDate ngaySinh;      // dtpNgaySinh     — bắt buộc
    private String    gioiTinh;      // "Nam" | "Nữ"    — bắt buộc
    private String    diaChi;        // txtDiaChi       — bắt buộc
    private String    danToc;        // txtDanToc       — bắt buộc
    private String    tonGiao;       // txtTonGiao      — bắt buộc

    // ── Thông tin phụ huynh ───────────────────────────────────────────────
    private String hoTenCha;         // txtHoTenCha     — bắt buộc
    private String hoTenMe;          // txtHoTenMe      — bắt buộc
    private String soDienThoai;      // txtSDT          — bắt buộc

    // ── Phân lớp (không bắt buộc khi tạo mới) ────────────────────────────
    private Integer classId;         // FK → classes.class_id

    // ── Trạng thái ─────────────────────────────────────────────────────
    private String trangThai;        // "Đang học" | "Nghỉ học" | "Chuyển trường" | "Bảo lưu"

    // ── Getters / Setters ─────────────────────────────────────────────────

    public String    getHoTen()                          { return hoTen; }
    public void      setHoTen(String hoTen)              { this.hoTen = hoTen; }

    public LocalDate getNgaySinh()                       { return ngaySinh; }
    public void      setNgaySinh(LocalDate ngaySinh)     { this.ngaySinh = ngaySinh; }

    public String    getGioiTinh()                       { return gioiTinh; }
    public void      setGioiTinh(String gioiTinh)        { this.gioiTinh = gioiTinh; }

    public String    getDiaChi()                         { return diaChi; }
    public void      setDiaChi(String diaChi)            { this.diaChi = diaChi; }

    public String    getDanToc()                         { return danToc; }
    public void      setDanToc(String danToc)            { this.danToc = danToc; }

    public String    getTonGiao()                        { return tonGiao; }
    public void      setTonGiao(String tonGiao)          { this.tonGiao = tonGiao; }

    public String    getHoTenCha()                       { return hoTenCha; }
    public void      setHoTenCha(String hoTenCha)        { this.hoTenCha = hoTenCha; }

    public String    getHoTenMe()                        { return hoTenMe; }
    public void      setHoTenMe(String hoTenMe)          { this.hoTenMe = hoTenMe; }

    public String    getSoDienThoai()                    { return soDienThoai; }
    public void      setSoDienThoai(String soDienThoai)  { this.soDienThoai = soDienThoai; }

    public Integer   getClassId()                        { return classId; }
    public void      setClassId(Integer classId)         { this.classId = classId; }

    public String    getTrangThai()                      { return trangThai; }
    public void      setTrangThai(String trangThai)      { this.trangThai = trangThai; }
}