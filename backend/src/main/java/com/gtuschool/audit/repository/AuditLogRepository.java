package com.gtuschool.audit.repository;

import com.gtuschool.audit.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho bảng audit_logs.
 * FR-57: Ghi lại lịch sử chỉnh sửa dữ liệu.
 * FR-58: Xem lịch sử chỉnh sửa.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    /**
     * FR-58: Lấy lịch sử theo bảng
     */
    List<AuditLog> findByTableNameOrderByActionTimeDesc(String tableName);

    /**
     * FR-58: Lấy lịch sử theo người thực hiện
     */
    List<AuditLog> findByUserIdOrderByActionTimeDesc(Integer userId);
}
