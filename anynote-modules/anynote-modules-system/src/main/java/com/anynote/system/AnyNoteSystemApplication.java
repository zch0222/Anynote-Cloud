package com.anynote.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统模块
 *
 * @author 称霸幼儿园
 */
@SpringBootApplication
public class AnyNoteSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnyNoteSystemApplication.class, args);
        System.out.println("系统模块启动成功");
    }
}
