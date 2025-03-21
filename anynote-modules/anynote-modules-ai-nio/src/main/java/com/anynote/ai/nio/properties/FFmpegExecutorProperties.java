package com.anynote.ai.nio.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * ffmpeg线程池配置
 * @author 称霸幼儿园
 */
@Component
@Data
@RefreshScope
@ConfigurationProperties(prefix = "executor.ffmpeg-executor")
public class FFmpegExecutorProperties {


    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer queueCapacity;
}
