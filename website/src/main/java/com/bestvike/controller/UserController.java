package com.bestvike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

//    @GetMapping("/user")
//    public String user(@AuthenticationPrincipal Principal principal, Model model){
//        model.addAttribute("username", principal.getName());
//        return "user/user";
//    }


    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
