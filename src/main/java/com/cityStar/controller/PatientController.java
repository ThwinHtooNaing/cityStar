package com.cityStar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cityStar.dto.PatientDTO;
import com.cityStar.security.CustomUserDetails;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {
    
    @GetMapping("/home")
    public String Home(@AuthenticationPrincipal CustomUserDetails user,
                        HttpSession session,
                        Model model) {
        PatientDTO patient = getPatient(user);
        model.addAttribute("current_user", patient);
        return "patient/home-page";
    }

    private PatientDTO getPatient(CustomUserDetails user){
        return new PatientDTO(user.getFirstName(),user.getLastName(),user.getProfilePath());
    }
    
}
