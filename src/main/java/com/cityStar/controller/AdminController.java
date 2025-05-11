package com.cityStar.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        System.out.println("Dashboard");
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getPrincipal());
        return "admin/admin-dashboard";
    }
}
