package com.han.gulimall.search.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.han.gulimall.search.controller
 * @Author XSH
 * @Date 2022-10-19 16:54
 * @Version 1.0.0
 * @Description：
 **/

@RestController
@RequestMapping("/search/test")
public class TestController {

    @RequestMapping("/hello")
    public String test() {

        return "hello,elasticsearch";

    }

}
