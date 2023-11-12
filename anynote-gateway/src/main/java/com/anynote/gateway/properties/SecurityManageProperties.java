package com.anynote.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员过滤
 * @author 称霸幼儿园
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "security.manage")
public class SecurityManageProperties {

    private List<String> urls = new ArrayList<>();
}
