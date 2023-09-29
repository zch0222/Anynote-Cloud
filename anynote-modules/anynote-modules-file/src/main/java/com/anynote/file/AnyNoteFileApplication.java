package com.anynote.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 称霸幼儿园
 */
@SpringBootApplication
public class AnyNoteFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnyNoteFileApplication.class, args);
        System.out.println("文件模块，启动！");
    }
}
