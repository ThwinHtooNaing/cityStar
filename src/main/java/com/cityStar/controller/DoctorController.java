package com.cityStar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    
    @GetMapping("/dashboard")
    public String dashBoard() {
        return "doctor/doctor-dashboard";
    }
    
}
