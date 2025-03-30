package com.anynote.ai.nio.config;

import com.anynote.ai.nio.properties.FFmpegExecutorProperties;
import com.anynote.ai.nio.properties.WhisperExecutorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {

    @Resource
    private WhisperExecutorProperties whisperExecutorProperties;

    @Resource
    private FFmpegExecutorProperties fFmpegExecutorProperties;

    /**
     * whisper 任务线程池
     * @return whisper 任务线程池
     */
    @Bean("whisperExecutor")
    public Executor whisperExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(whisperExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(whisperExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(whisperExecutorProperties.getQueueCapacity());
        executor.setThreadNamePrefix("whisperExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setKeepAliveSeconds(60);
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }

    @Bean("ioExecutor")
    public Executor ioExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(whisperExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(whisperExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(whisperExecutorProperties.getQueueCapacity());
        executor.setThreadNamePrefix("ioExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setKeepAliveSeconds(60);
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }

    /**
     * ffmpeg 线程池
     * @return ffmpeg 线程池
     */
    @Bean("ffmpegExecutor")
    public Executor ffmpegExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(fFmpegExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(fFmpegExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(fFmpegExecutorProperties.getQueueCapacity());
        executor.setThreadNamePrefix("ffmpegExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setKeepAliveSeconds(60);
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }
}
