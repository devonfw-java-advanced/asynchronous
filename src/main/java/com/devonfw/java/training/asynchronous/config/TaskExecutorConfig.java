package com.devonfw.java.training.asynchronous.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class TaskExecutorConfig {

    @Bean
    @Primary
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setQueueCapacity(0);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("task-executor-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setQueueCapacity(0);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }
}
