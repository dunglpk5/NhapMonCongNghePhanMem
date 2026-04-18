package com.gtuschool.audit.service;

import com.gtuschool.audit.model.AuditLog;
import com.gtuschool.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Logging Module Implementation.
 * FR-57: Ghi lại lịch sử chỉnh sửa dữ liệu bao gồm:
 *   người thực hiện, thời gian, loại thao tác, nội dung thay đổi.
 * NFR-06: Ghi nhận 100% lịch sử chỉnh sửa dữ liệu quan trọng.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("null")
public class LoggingServiceImpl implements ILoggingService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void logInsert(String tableName, Integer userId, String newData) {
        try {
            AuditLog auditLog = AuditLog.builder()
                    .tableName(tableName)
                    .action("INSERT")
                    .userId(userId)
                    .newData(newData)
                    .build();
            auditLogRepository.save(auditLog);
            log.debug("Audit log INSERT: table={}, userId={}", tableName, userId);
        } catch (Exception e) {
            // NFR-08: Ghi log lỗi nhưng không ảnh hưởng luồng chính
            log.error("Lỗi ghi audit log INSERT: {}", e.getMessage());
        }
    }

    @Override
    public void logUpdate(String tableName, Integer userId, String oldData, String newData) {
        try {
            AuditLog auditLog = AuditLog.builder()
                    .tableName(tableName)
                    .action("UPDATE")
                    .userId(userId)
                    .oldData(oldData)
                    .newData(newData)
                    .build();
            auditLogRepository.save(auditLog);
            log.debug("Audit log UPDATE: table={}, userId={}", tableName, userId);
        } catch (Exception e) {
            log.error("Lỗi ghi audit log UPDATE: {}", e.getMessage());
        }
    }

    @Override
    public void logDelete(String tableName, Integer userId, String oldData) {
        try {
            AuditLog auditLog = AuditLog.builder()
                    .tableName(tableName)
                    .action("DELETE")
                    .userId(userId)
                    .oldData(oldData)
                    .build();
            auditLogRepository.save(auditLog);
            log.debug("Audit log DELETE: table={}, userId={}", tableName, userId);
        } catch (Exception e) {
            log.error("Lỗi ghi audit log DELETE: {}", e.getMessage());
        }
    }
}
