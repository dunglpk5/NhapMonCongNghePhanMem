package com.example.nmcnpm.module.logging.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * AuditLog entity – ánh xạ từ class diagram (entity audit_logs).
 * FR-57: ghi lịch sử thao tác. NFR-06: 100% thao tác quan trọng.
 */
@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false, length = 50)
    private String action;            // LOGIN_SUCCESS | LOGIN_FAILED | LOGOUT | RESET_PW

    @Column(name = "table_name", length = 100)
    private String tableName;

    @Column(name = "record_id")
    private Integer recordId;

    @Column(name = "action_time", updatable = false)
    @Builder.Default
    private LocalDateTime actionTime = LocalDateTime.now();

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "meta_data", columnDefinition = "TEXT")
    private String metaData;          // JSON extra info (role, username...)
}
