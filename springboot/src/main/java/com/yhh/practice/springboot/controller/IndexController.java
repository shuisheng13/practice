package com.yhh.practice.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public Object index(){
        return "index";
    }
    @RequestMapping("/index2")
    public Object index2(){
        return "index2";
    }
    @RequestMapping("/index3")
    public Object index3(){
        return "index3";
    }
}
