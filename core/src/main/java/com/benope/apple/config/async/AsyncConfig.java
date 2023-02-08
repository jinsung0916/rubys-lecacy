package com.benope.apple.config.async;

import io.sentry.spring.SentryTaskDecorator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@ConditionalOnWebApplication
@EnableAsync
@Configuration
public class AsyncConfig {

    private static final String DEFAULT_THREAD_NAME_PREFIX = "BENOPE-ASYNC-";

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix(DEFAULT_THREAD_NAME_PREFIX);

        // for Sentry
        executor.setTaskDecorator(new SentryTaskDecorator());
        return executor;
    }

}
