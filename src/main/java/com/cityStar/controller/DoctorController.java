package com.cityStar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cityStar.dto.AvailabilityDTO;
import com.cityStar.dto.DoctorDTO;
import com.cityStar.model.Availability;
import com.cityStar.model.Doctor;
import com.cityStar.rowmapper.AvailabilityRowMapper;
import com.cityStar.rowmapper.DoctorRowMapper;
import com.cityStar.security.CustomUserDetails;
import com.cityStar.service.DoctorService;
import com.cityStar.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final UserService userService;
    private final DoctorService doctorService;
    public DoctorController(UserService userService,DoctorService doctorService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }
    
    @GetMapping("/dashboard")
    public String Dashboard(@AuthenticationPrincipal CustomUserDetails user,
                             Model model) {
        DoctorDTO doctor = getDoctor(user);
        model.addAttribute("current_user", doctor);
        return "doctor/doctor-dashboard";
    }

    @GetMapping("/appointments")
    public String appointments(@AuthenticationPrincipal CustomUserDetails user,
                                Model model) {
        DoctorDTO doctor = getDoctor(user);
        model.addAttribute("current_user", doctor);
        return "doctor/doctor-appointments";
    }

    @GetMapping("/profile")
    public String Profile(@AuthenticationPrincipal CustomUserDetails user,
                           Model model) {
        DoctorDTO doctor = getDoctor(user);
        DoctorDTO doctorProfile = getDoctorProfile((Doctor) userService.findByEmail(user.getUsername()));
        model.addAttribute("current_user", doctor);
        model.addAttribute("doctor_profile", doctorProfile);
        return "doctor/doctor-profile";
    }

    @PostMapping("/availability")
    @ResponseBody
    public ResponseEntity<?> availability(@AuthenticationPrincipal CustomUserDetails user,
                                          @RequestBody AvailabilityDTO availability,
                                          Model model) {
        try{
            Doctor doctor = (Doctor)userService.findByEmail(user.getUsername());
            Availability record = AvailabilityRowMapper.toEntity(availability,doctor);
            doctorService.addAvailability(record);
            return ResponseEntity.ok("Availability added successfully");
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to save availability: " + e.getMessage());
        }
        
    }
    
    
    private DoctorDTO getDoctor(CustomUserDetails user){
        Doctor doctor = (Doctor) userService.findByEmail(user.getUsername());
        return new DoctorDTO(doctor.getFirstName(), doctor.getLastName(), doctor.getProfilePath(),doctor.getSpeciality());
    }

    private DoctorDTO getDoctorProfile(Doctor doctor){
        return DoctorRowMapper.toDtoWithoutPassword(doctor);
    }
    
}
