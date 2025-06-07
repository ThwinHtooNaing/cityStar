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
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PatchMapping(value = "/profile", consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<?> Profile(@AuthenticationPrincipal CustomUserDetails user,
                                     @RequestPart(value = "doctor",required = false) DoctorDTO doctorDTO,
                                     @RequestPart(required = false) MultipartFile file) {
        try{
            doctorService.updateDoctorProfile(user.getUsername(), doctorDTO, file);
        }catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed Due to: " + e.getMessage());
        }
        return ResponseEntity.ok("Profile updated successfully.");
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
                                          @RequestBody AvailabilityDTO availability) {
        try{
            Doctor doctor = (Doctor) userService.findByEmail(user.getUsername());
            Availability record = AvailabilityRowMapper.toEntity(availability,doctor);
            doctorService.addAvailability(record);
            return ResponseEntity.ok("Availability added successfully");
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to save availability: " + e.getMessage());
        }
    }

    @GetMapping("/availability")
    @ResponseBody
    public ResponseEntity<?> availability(@AuthenticationPrincipal CustomUserDetails user,
                                           Model model) {
        Availability latest = doctorService.getLatestAvailabilityForToday(user.getUsername());
        if (latest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        AvailabilityDTO dto = AvailabilityRowMapper.toDto(latest);
        return ResponseEntity.ok(dto);
    }
    
    private DoctorDTO getDoctor(CustomUserDetails user){
        Doctor doctor = (Doctor) userService.findByEmail(user.getUsername());
        return new DoctorDTO(doctor.getFirstName(), doctor.getLastName(), doctor.getProfilePath(),doctor.getSpeciality());
    }

    private DoctorDTO getDoctorProfile(Doctor doctor){
        return DoctorRowMapper.toDtoWithoutPassword(doctor);
    }
    
}
