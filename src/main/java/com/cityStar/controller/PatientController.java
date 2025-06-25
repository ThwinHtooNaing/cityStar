package com.cityStar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cityStar.dto.PatientDTO;
import com.cityStar.model.Patient;
import com.cityStar.rowmapper.PatientRowMapper;
import com.cityStar.security.CustomUserDetails;
import com.cityStar.service.PatientService;
import com.cityStar.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/patient")
public class PatientController {
    
    private final UserService userService;
    private final PatientService patientService;
    
    public PatientController(UserService userService,
                             PatientService patientService){
        this.patientService = patientService;
        this.userService = userService;
    }
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

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails user,
                           Model model) {
        PatientDTO patient = getPatient(user);
        PatientDTO patientProfile = getPatientProfile((Patient) userService.findByEmail(user.getUsername()));
        model.addAttribute("current_user", patient);
        model.addAttribute("patient_profile", patientProfile);
        return "patient/profile";
    }

    @GetMapping("/appointmentHistory")
    public String appointmentHistory(@AuthenticationPrincipal CustomUserDetails user,
                                      Model model) {
        PatientDTO patient = getPatient(user);
        model.addAttribute("current_user", patient);
        return "patient/appointment-history";
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
    
    private PatientDTO getPatientProfile(Patient patient){
        return PatientRowMapper.toDtoWithoutPassword(patient);
    }
}
