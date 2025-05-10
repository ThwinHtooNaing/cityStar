package com.cityStar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    
    @GetMapping("/")
    public String Home() {
        return "redirect:/auth/login";
    }
    
}
