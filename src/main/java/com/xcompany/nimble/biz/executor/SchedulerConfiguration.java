/*
 * Copyright (c) 2021.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.biz.executor;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author xiadong
 * @since 2021/10/25
 **/
@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {

    private final ThreadPoolTaskScheduler taskScheduler;

    public SchedulerConfiguration(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}
