package com.example.nmcnpm.module.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * AsyncConfig – cấu hình thread pool cho @Async (dùng trong LoggingService).
 * Ghi audit log bất đồng bộ để không ảnh hưởng response time (NFR-01).
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "loggingExecutor")
    public Executor loggingExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("audit-log-");
        executor.initialize();
        return executor;
    }
}
