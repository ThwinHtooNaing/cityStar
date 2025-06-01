package com.cityStar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cityStar.dto.DoctorDTO;
import com.cityStar.model.Doctor;
import com.cityStar.rowmapper.DoctorRowMapper;
import com.cityStar.security.CustomUserDetails;
import com.cityStar.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final UserService userService;
    public DoctorController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/dashboard")
    public String Dashboard(@AuthenticationPrincipal CustomUserDetails user,
                            HttpSession session,
                            Model model) {
        DoctorDTO doctor = getDoctor(user);
        model.addAttribute("current_user", doctor);
        return "doctor/doctor-dashboard";
    }

    @GetMapping("/appointments")
    public String appointments(@AuthenticationPrincipal CustomUserDetails user,
                            HttpSession session,
                            Model model) {
        DoctorDTO doctor = getDoctor(user);
        model.addAttribute("current_user", doctor);
        return "doctor/doctor-appointments";
    }

    @GetMapping("/profile")
    public String Profile(@AuthenticationPrincipal CustomUserDetails user,
                           HttpSession session,
                           Model model) {
        DoctorDTO doctor = getDoctor(user);
        DoctorDTO doctorProfile = getDoctorProfile((Doctor) userService.findByEmail(user.getUsername()));
        model.addAttribute("current_user", doctor);
        model.addAttribute("doctor_profile", doctorProfile);
        return "doctor/doctor-profile";
    }
    
    private DoctorDTO getDoctor(CustomUserDetails user){
        Doctor doctor = (Doctor) userService.findByEmail(user.getUsername());
        return new DoctorDTO(doctor.getFirstName(), doctor.getLastName(), doctor.getProfilePath(),doctor.getSpeciality());
    }

    private DoctorDTO getDoctorProfile(Doctor doctor){
        return DoctorRowMapper.toDtoWithoutPassword(doctor);
    }
    
}
