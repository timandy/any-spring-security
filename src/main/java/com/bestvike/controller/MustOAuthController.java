package com.bestvike.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 许崇雷 on 2017-09-14.
 */
@RestController
public class MustOAuthController {
    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public String permitAll() {
        return "permitAll";
    }

    @GetMapping("/hasAny")
    @PreAuthorize("hasAnyAuthority()")
    public String any() {
        return "hasAnyAuthority";
    }

    @GetMapping("/hasUser")
    @PreAuthorize("hasRole('USER')")
    public String getDemo() {
        return "hasRole_USER";
    }

    @GetMapping("/hasAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String hasAdmin() {
        return "hasRole_ADMIN";
    }
}
