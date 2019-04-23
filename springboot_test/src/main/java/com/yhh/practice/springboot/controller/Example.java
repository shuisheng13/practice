package com.yhh.practice.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Example {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }
    @RequestMapping("/hello")
    public Object sayHello() {
        return "hello";
    }



}
