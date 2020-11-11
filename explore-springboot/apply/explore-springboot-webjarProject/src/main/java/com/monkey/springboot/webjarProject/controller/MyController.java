package com.monkey.springboot.webjarProject.controller;

import com.monkey.springboot.webjarProject.exception.MyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by smlz on 2019/3/19.
 */
@RestController
public class MyController {

    @RequestMapping("/test")
    public String test() {
        return "hello";
    }

    @RequestMapping("/testError")
    public String testError() {
        throw new MyException(100,"用户不存在");
    }
}
