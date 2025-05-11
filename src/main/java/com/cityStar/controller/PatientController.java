package com.cityStar.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {
    
    @GetMapping("/home")
    public String Home() {
        System.out.println("Dashboard");
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getPrincipal());
        return "patient/home-page";
    }
    
}
