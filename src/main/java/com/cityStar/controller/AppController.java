package com.cityStar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @RequestMapping("/login")
    public String LogIn() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }
    
}
