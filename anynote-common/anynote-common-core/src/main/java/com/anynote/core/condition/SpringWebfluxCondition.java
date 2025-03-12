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
        try {
            classLoader.loadClass("org.springframework.web.servlet.DispatcherServlet");
            // 如果存在 DispatcherServlet，说明是 Spring MVC 环境
            return false;
        } catch (ClassNotFoundException e) {
            // 如果不存在 DispatcherServlet，说明是 Spring WebFlux 环境
            return context.getClassLoader()
                    .getResource("org/springframework/cloud/gateway/config/GatewayAutoConfiguration.class") == null;
        }
    }
}
