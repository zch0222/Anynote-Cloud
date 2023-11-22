package com.anynote.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "anynote.external-resources")
public class ExternalResourcesProperties {

    private List<String> allowedDomains = new ArrayList<>();
}
