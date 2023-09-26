package com.anynote.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "security.ignore")
public class SecurityIgnoreProperties {

    private List<String> whites = new ArrayList<>();
}
