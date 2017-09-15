package com.bestvike.controller;

import com.bestvike.config.service.DomainUserDetailService;
import com.bestvike.entity.UserInfo;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping({"/", "/index", "/home"})
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication == null || authentication instanceof AnonymousAuthenticationToken)
                ? "login"
                : "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(UserInfo userInfo) {
        // 此处省略校验逻辑
        if (DomainUserDetailService.insert(userInfo))
            return "redirect:register?success";
        return "redirect:register?error";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "user/user";
    }
}
