package com.example.nmcnpm.module.logging.service;

public interface ILoggingService {

    void logAction(Integer userId, String action, String entityId);

    void logLoginEvent(Integer userId, boolean success, String ipAddress, String meta);
}
