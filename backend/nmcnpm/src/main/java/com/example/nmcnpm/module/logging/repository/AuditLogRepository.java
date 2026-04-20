package com.example.nmcnpm.module.logging.repository;

import com.example.nmcnpm.module.logging.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {}
