package com.cityStar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cityStar.dto.DoctorDTO;
import com.cityStar.security.CustomUserDetails;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    
    @GetMapping("/dashboard")
    public String Dashboard(@AuthenticationPrincipal CustomUserDetails user,
                            HttpSession session,
                            Model model) {
        DoctorDTO doctor = getDoctor(user);
        model.addAttribute("current_user", doctor);
        return "doctor/doctor-dashboard";
    }

    private DoctorDTO getDoctor(CustomUserDetails user){
        return new DoctorDTO(user.getFirstName(),user.getLastName(),user.getProfilePath());
    }
    
}
