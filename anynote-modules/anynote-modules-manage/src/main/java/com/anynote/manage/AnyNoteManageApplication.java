package com.anynote.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 称霸幼儿园
 */
@SpringBootApplication
public class AnyNoteManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnyNoteManageApplication.class, args);
        System.out.println("管理模块，启动!");
    }
}
