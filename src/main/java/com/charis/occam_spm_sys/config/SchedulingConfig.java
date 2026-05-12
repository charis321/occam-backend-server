package com.charis.occam_spm_sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulingConfig {
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        // 設定執行緒池大小，建議根據同時在線的課程數量調整
        scheduler.setPoolSize(10); 
        // 設定執行緒名稱前綴，方便在 Log 中排查問題
        scheduler.setThreadNamePrefix("Rollcall-Scheduler-");
        // 初始化
        scheduler.initialize();
        return scheduler;
    }
}
