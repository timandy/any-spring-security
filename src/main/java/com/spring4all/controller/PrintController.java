package com.spring4all.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 许崇雷 on 2017/8/17.
 */
@RestController
public class PrintController {
    @GetMapping("/print")
    public String print(String value) {
        System.out.println(value);
        return value;
    }
}
