package com.gtuschool.audit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho bảng audit_logs trong database.
 * FR-57: Hệ thống tự động ghi lại lịch sử chỉnh sửa dữ liệu.
 * NFR-06: Ghi nhận 100% lịch sử chỉnh sửa dữ liệu quan trọng.
 */
@Entity
@Table(name = "audit_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    /**
     * Tên bảng bị thay đổi (ví dụ: students, classes)
     */
    @Column(name = "table_name", length = 100)
    private String tableName;

    /**
     * Loại thao tác: INSERT, UPDATE, DELETE
     */
    @Column(name = "action", length = 10)
    private String action;

    /**
     * ID người thực hiện thao tác
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * Thời gian thực hiện thao tác
     */
    @Column(name = "action_time")
    private LocalDateTime actionTime;

    /**
     * Dữ liệu cũ (trước khi thay đổi) - JSON format
     */
    @Column(name = "old_data", columnDefinition = "NVARCHAR(MAX)")
    private String oldData;

    /**
     * Dữ liệu mới (sau khi thay đổi) - JSON format
     */
    @Column(name = "new_data", columnDefinition = "NVARCHAR(MAX)")
    private String newData;

    @PrePersist
    protected void onCreate() {
        if (actionTime == null) {
            actionTime = LocalDateTime.now();
        }
    }
}
