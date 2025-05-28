package com.cityStar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {
    
    @GetMapping("/session-expired")
    public String sessionExpired(Model model) {
        model.addAttribute("message", "Your session has expired. Please login again.");
        return "session-expired"; // View name
    }
}
