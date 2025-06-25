package com.cityStar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cityStar.dto.PatientDTO;
import com.cityStar.model.Patient;
import com.cityStar.rowmapper.PatientRowMapper;
import com.cityStar.security.CustomUserDetails;
import com.cityStar.service.PatientService;
import com.cityStar.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;


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

    @PatchMapping("/profile")
    @ResponseBody
    public ResponseEntity<?> profile(@AuthenticationPrincipal CustomUserDetails user,
                                     @RequestPart(value = "patient", required = false) PatientDTO patientDTO,
                                     @RequestPart(required = false) MultipartFile file) {
        try
        {
            patientService.updatePatientProfile(user.getUsername(), patientDTO, file);
        }
        catch (Exception e) {
           return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed Due to: " + e.getMessage());
        }
        return ResponseEntity.ok("Profile updated successfully.");
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
        Patient patient = (Patient) userService.findByEmail(user.getUsername());
        return new PatientDTO(patient.getFirstName(),patient.getLastName(),patient.getProfilePath());
    }
    
    private PatientDTO getPatientProfile(Patient patient){
        return PatientRowMapper.toDtoWithoutPassword(patient);
    }
}
