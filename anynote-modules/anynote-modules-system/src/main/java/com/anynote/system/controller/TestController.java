package com.anynote.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 Controller
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("hello")
    public String helloWorld() {
        return "hello world";
    }
}
