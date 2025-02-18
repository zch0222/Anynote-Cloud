package com.anynote.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class SpringWebfluxCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 检查是否存在 Spring Webflux 的关键类
        ClassLoader classLoader = context.getClassLoader();
        if (classLoader == null) {
            return false;
        }

        // 检查是否存在关键类
        try {
            classLoader.loadClass("org.springframework.web.reactive.DispatcherHandler");
            // 排除GateWay
            return context.getClassLoader()
                    .getResource("org/springframework/cloud/gateway/config/GatewayAutoConfiguration.class") == null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
}
