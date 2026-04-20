package com.example.nmcnpm.module.logging.service.impl;

import com.example.nmcnpm.module.logging.service.ILoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements ILoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingServiceImpl.class);

    @Override
    public void logAction(Integer userId, String action, String entityId) {
        LOGGER.info("audit userId={} action={} entityId={}", userId, action, entityId);
    }
}
