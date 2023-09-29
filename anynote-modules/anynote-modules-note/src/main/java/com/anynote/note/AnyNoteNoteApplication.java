package com.anynote.note;

import com.anynote.common.security.annotation.EnableAnyNoteFeignClients;
import com.anynote.common.security.annotation.EnableCustomConfig;
import com.anynote.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AnyNote Note模块启动类
 * @author 称霸幼儿园
 */
@EnableCustomSwagger2
@EnableAnyNoteFeignClients
@EnableCustomConfig
@SpringBootApplication
public class AnyNoteNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnyNoteNoteApplication.class, args);
        System.out.println("笔记模块启动");
    }
}
