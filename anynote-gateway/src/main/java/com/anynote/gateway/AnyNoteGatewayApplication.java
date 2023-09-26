package com.anynote.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 *
 * @author 称霸幼儿园
 */
@SpringBootApplication
public class AnyNoteGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnyNoteGatewayApplication.class, args);
        System.out.println("网关模块启动成功");
    }
}
