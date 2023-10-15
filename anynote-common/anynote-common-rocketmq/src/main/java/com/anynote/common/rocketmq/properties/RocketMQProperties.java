package com.anynote.common.rocketmq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 称霸幼儿园
 */
@Component
@Data
@ConfigurationProperties(prefix = "anynote.data.rocketmq")
public class RocketMQProperties {

    private String noteTaskTopic;

    private String noteTaskGroup;

    private String noteTopic;

    private String noteGroup;
}
