package com.cityStar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cityStar.dto.PatientDTO;
import com.cityStar.security.CustomUserDetails;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {
    
    @GetMapping("/home")
    public String Home(@AuthenticationPrincipal CustomUserDetails user,
                        Model model) {
        PatientDTO patient = getPatient(user);
        model.addAttribute("current_user", patient);
        return "patient/home-page";
    }

    @GetMapping("/findDoctor")
    public String FindDoctor(@AuthenticationPrincipal CustomUserDetails user,
                              Model model) {
        PatientDTO patient = getPatient(user);
        model.addAttribute("current_user", patient);
        return "patient/find-Doctor";
    }

    @GetMapping("/aboutUs")
    public String AboutUS(@AuthenticationPrincipal CustomUserDetails user,
                              Model model) {
        PatientDTO patient = getPatient(user);
        model.addAttribute("current_user", patient);
        return "patient/about-us";
    }

    private PatientDTO getPatient(CustomUserDetails user){
        return new PatientDTO(user.getFirstName(),user.getLastName(),user.getProfilePath());
    }
    
}
