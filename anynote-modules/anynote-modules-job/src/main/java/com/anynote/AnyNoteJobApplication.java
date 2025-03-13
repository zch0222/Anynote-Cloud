package com.anynote;

import com.anynote.common.security.annotation.EnableAnyNoteFeignClients;
import com.anynote.common.security.annotation.EnableCustomConfig;
import com.anynote.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomSwagger2
@EnableAnyNoteFeignClients
@EnableCustomConfig
@SpringBootApplication
public class AnyNoteJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnyNoteJobApplication.class, args);
        System.out.println("Job模块，启动！");
    }
}