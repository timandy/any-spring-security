package com.bestvike.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 许崇雷 on 2017-09-14.
 */
@RestController
public class MustOAuthController {
    @GetMapping("/oauth/print")
    public String permitAll(String value) {
        return value + " from oauth";
    }
}
