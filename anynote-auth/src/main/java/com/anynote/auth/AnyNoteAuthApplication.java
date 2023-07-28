package com.anynote.auth;


import com.anynote.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author 称霸幼儿园
 */
@EnableCustomSwagger2
@SpringBootApplication
public class AnyNoteAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnyNoteAuthApplication.class, args);
        System.out.println("认证模块启动");
    }
}
