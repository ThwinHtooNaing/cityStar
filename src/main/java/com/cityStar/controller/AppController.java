package com.cityStar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/com")
@Controller
public class AppController {

    @RequestMapping("/login")
    public String LogIn() {
        return "home";
    }
    
}
