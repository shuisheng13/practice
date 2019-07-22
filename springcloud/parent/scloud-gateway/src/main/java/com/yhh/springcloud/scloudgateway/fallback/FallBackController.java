package com.yhh.springcloud.scloudgateway.fallback;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @RequestMapping("/fallback")
    public String fallback() {
        return "fallback";
    }


}
