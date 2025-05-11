package com.cityStar.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    
    @GetMapping("/dashboard")
    public String dashBoard() {
        System.out.println("Dashboard");
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getPrincipal());
        return "doctor/doctor-dashboard";
    }
    
}
