package com.bestvike.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 许崇雷 on 2017/8/17.
 */
@RestController
public class MustBasicController {
    //在 WebSecurityConfiguration 控制访问权限
    @GetMapping("/print")
    public String print(String value) {
        System.out.println(value);
        return value;
    }
}
