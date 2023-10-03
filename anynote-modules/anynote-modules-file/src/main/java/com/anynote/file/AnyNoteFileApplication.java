package com.anynote.file;

import com.anynote.common.security.annotation.EnableAnyNoteFeignClients;
import com.anynote.common.security.annotation.EnableCustomConfig;
import com.anynote.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 称霸幼儿园
 */
@EnableCustomSwagger2
@EnableAnyNoteFeignClients
@EnableCustomConfig
@SpringBootApplication
public class AnyNoteFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnyNoteFileApplication.class, args);
        System.out.println("文件模块，启动！");
    }
}
