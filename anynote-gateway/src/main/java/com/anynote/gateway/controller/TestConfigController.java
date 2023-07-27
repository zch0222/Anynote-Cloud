package com.anynote.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试nacos配置
 * @author 称霸幼儿园
 */
@RefreshScope
@RestController
public class TestConfigController {


    @Value("${anynote.module.name}")
    private String name;

    @GetMapping("/gateway/config")
    public String testConfig() {
        return name;
    }

}
