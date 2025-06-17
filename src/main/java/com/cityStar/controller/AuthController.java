package com.cityStar.controller;

import com.cityStar.model.Doctor;
import com.cityStar.model.Patient;
import com.cityStar.model.User;
import com.cityStar.rowmapper.DoctorRowMapper;
import com.cityStar.rowmapper.PatientRowMapper;
import com.cityStar.service.UserService;

import jakarta.validation.Valid;

import com.cityStar.dto.DoctorDTO;
import com.cityStar.dto.PatientDTO;
import com.cityStar.enums.Role;
import com.cityStar.exception.EmailDuplicationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, 
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByEmail(email);

            switch (user.getRole()) {
                case Role.ADMIN:    
                    return "redirect:/admin/dashboard";
                case Role.DOCTOR:
                    return "redirect:/doctor/dashboard";
                case Role.PATIENT:
                    return "redirect:/patient/home";
                default:
                    return "redirect:/auth/login";
            }
        }catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("user_error", "User not found");
            return "redirect:/auth/login";
        } catch (BadCredentialsException e) {
            redirectAttributes.addFlashAttribute("password_error", "Incorrect password");
            return "redirect:/auth/login";
        } catch(Exception e) {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("doctor")) {
            model.addAttribute("doctor", new DoctorDTO());
        }
        if (!model.containsAttribute("patient")) {
            model.addAttribute("patient", new PatientDTO());
        }
        return "register";
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(@Valid @ModelAttribute("doctor") DoctorDTO doctor,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", new PatientDTO());
            return "register";
        }
        doctor.setRole(Role.DOCTOR);
        Doctor data = DoctorRowMapper.toEntity(doctor);
        try {
            userService.saveUser(data);
        }catch (EmailDuplicationException e) {
            model.addAttribute("email_error","email is duplicated!!");
            return "register";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/register/patient")
    public String registerPatient(@Valid @ModelAttribute("patient") PatientDTO patient,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            // model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("doctor", new DoctorDTO());
            return "register";
        }
        patient.setRole(Role.PATIENT);
        Patient data = PatientRowMapper.toEntity(patient);
        try {
            userService.saveUser(data);
        }catch (EmailDuplicationException e) {
            model.addAttribute("email_error","email is duplicated!!");
            model.addAttribute("doctor", new DoctorDTO());
            return "register";
        }
        return "redirect:/auth/login";
    }

}
