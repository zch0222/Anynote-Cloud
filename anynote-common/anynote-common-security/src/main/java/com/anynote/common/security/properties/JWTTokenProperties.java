package com.anynote.common.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置
 * @author 称霸幼儿园
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "anynote.jwt-setting")
public class JWTTokenProperties {
    /**
     * token 默认过期时间
     */
    private long tokenExpireTime;

    /**
     * token
     */
    private String secret;
}
