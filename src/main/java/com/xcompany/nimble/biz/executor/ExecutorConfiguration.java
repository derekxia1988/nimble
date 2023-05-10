/*
 * Copyright (c) 2021.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.biz.executor;

import com.xcompany.nimble.base.executor.OrderedExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.*;

/**
 * @author xiadong
 * @since 2021/09/14
 **/
@Configuration
@ConfigurationProperties("executor")
@Log4j2
public class ExecutorConfiguration {

    private int scheduledSize = Math.max(4, Runtime.getRuntime().availableProcessors() / 4);

    @Bean
    public OrderedExecutor<String> bizExecutor() {
        return new BizExecutor(
                new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
                        60L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>()),
                (throwable -> log.error("biz executor error.", throwable)));
    }

    @Bean
    ScheduledThreadPoolExecutor scheduledPool(ThreadPoolTaskScheduler taskScheduler) {
        return taskScheduler.getScheduledThreadPoolExecutor();
    }

    @Bean
    ThreadPoolTaskScheduler taskScheduler() {
        var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(scheduledSize);
        threadPoolTaskScheduler.setThreadNamePrefix("game-scheduler-");
        return threadPoolTaskScheduler;
    }

    public void setScheduledSize(int scheduledSize) {
        this.scheduledSize = scheduledSize;
    }

}