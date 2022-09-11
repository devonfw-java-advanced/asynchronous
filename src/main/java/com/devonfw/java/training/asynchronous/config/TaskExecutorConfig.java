package com.devonfw.java.training.asynchronous.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class TaskExecutorConfig {

    @Bean
    @Primary
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setQueueCapacity(0);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("primary-");
        executor.initialize();
        return executor;
    }
}
