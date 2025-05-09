package com.cityStar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {
    
    @GetMapping("/home")
    public String Home() {
        return "patient/home-page";
    }
    
}
