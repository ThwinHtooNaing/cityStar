package com.cityStar.controller;

import com.cityStar.model.Doctor;
import com.cityStar.model.Patient;
import com.cityStar.model.User;
import com.cityStar.repository.IuserRepository;
import com.cityStar.service.UserService;
import com.cityStar.dto.DoctorDTO;
import com.cityStar.dto.PatientDTO;
import com.cityStar.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final IuserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          IuserRepository userRepository, 
                          PasswordEncoder passwordEncoder,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(email).orElseThrow();

        switch (user.getRole()) {
            case Role.ADMIN:
                return "redirect:/admin/dashboard";
            case Role.DOCTOR:
                return "redirect:/doctor/home";
            case Role.PATIENT:
                return "redirect:/patient/home";
            default:
                return "redirect:/";
        }
    }

    @GetMapping("/register/doctor")
    public String registerDoctorPage() {
        return "registerDoctor";
    }

    @GetMapping("/register/patient")
    public String registerPatientPage() {
        return "registerPatient";
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(@RequestBody DoctorDTO doctor) {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setRole(Role.DOCTOR);
        Doctor data = new Doctor();
        data.setFirstName(doctor.getFirstName());
        data.setMiddleName(doctor.getMiddleName());
        data.setLastName(doctor.getLastName());
        data.setEmail(doctor.getEmail());
        data.setPassword(doctor.getPassword());
        data.setAddress(doctor.getAddress());
        data.setAge(doctor.getAge());
        data.setRole(doctor.getRole());
        data.setProfilePath(doctor.getProfilePath());
        data.setBio(doctor.getBio());
        data.setContactInfo(doctor.getContactInfo());
        data.setSpeciality(doctor.getSpecialty());
        data.setProfilePath(doctor.getProfilePath());
        userService.saveUser(data);
        return "redirect:/login";
    }

    @PostMapping("/register/patient")
    public String registerPatient(@RequestBody PatientDTO patient) {
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patient.setRole(Role.PATIENT);
        Patient data = new Patient();
        data.setFirstName(patient.getFirstName());
        data.setMiddleName(patient.getMiddleName());
        data.setLastName(patient.getLastName());
        data.setEmail(patient.getEmail());
        data.setPassword(patient.getPassword());
        data.setAddress(patient.getAddress());
        data.setAge(patient.getAge());
        data.setRole(patient.getRole());
        data.setProfilePath(patient.getProfilePath());
        userService.saveUser(data);
        return "redirect:/login";
    }

}
