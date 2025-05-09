package com.cityStar.controller;

import com.cityStar.model.Doctor;
import com.cityStar.model.Patient;
import com.cityStar.model.User;
import com.cityStar.rowmapper.DoctorRowMapper;
import com.cityStar.rowmapper.PatientRowMapper;
import com.cityStar.service.UserService;

import com.cityStar.dto.DoctorDTO;
import com.cityStar.dto.PatientDTO;
import com.cityStar.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, 
                          PasswordEncoder passwordEncoder,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
                    return "redirect:/";
            }
        }catch (UsernameNotFoundException e) {
            System.out.println("user not found");
            redirectAttributes.addFlashAttribute("user_error", "User not found");
            return "redirect:/auth/login";
        } catch (BadCredentialsException e) {
            System.out.println("Incorrect password");
            redirectAttributes.addFlashAttribute("password_error", "Incorrect password");
            return "redirect:/auth/login";
        } catch(Exception e) {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String registerDoctorPage() {
        return "register";
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(@ModelAttribute DoctorDTO doctor) {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setRole(Role.DOCTOR);
        Doctor data = DoctorRowMapper.toEntity(doctor);
        userService.saveUser(data);
        return "redirect:/auth/login";
    }

    @PostMapping("/register/patient")
    public String registerPatient(@ModelAttribute PatientDTO patient) {
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patient.setRole(Role.PATIENT);
        Patient data = PatientRowMapper.toEntity(patient);
        userService.saveUser(data);
        return "redirect:/auth/login";
    }

}
