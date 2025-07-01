package com.cityStar.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cityStar.dto.AppointmentDTO;
import com.cityStar.dto.AvailabilityDTO;
import com.cityStar.dto.PatientDTO;
import com.cityStar.model.Availability;
import com.cityStar.model.Patient;
import com.cityStar.rowmapper.AvailabilityRowMapper;
import com.cityStar.rowmapper.PatientRowMapper;
import com.cityStar.security.CustomUserDetails;
import com.cityStar.service.DoctorService;
import com.cityStar.service.NotificationService;
import com.cityStar.service.PatientService;
import com.cityStar.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/patient")
public class PatientController {
    
    private final UserService userService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final NotificationService notificationService;
    public PatientController(UserService userService,
                             PatientService patientService,
                             DoctorService doctorService,
                             NotificationService notificationService) {
        this.patientService = patientService;
        this.userService = userService;
        this.doctorService = doctorService;
        this.notificationService = notificationService;
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
        List<AvailabilityDTO> availabilities = doctorService.getAllAvailabilitiesForToday();
        model.addAttribute("availabilities", availabilities);
        model.addAttribute("current_user", patient);
        return "patient/find-Doctor";
    }

    @GetMapping("/availability/{id}")
    @ResponseBody
    public AvailabilityDTO getAvailabilityById(@PathVariable Long id) {
        Availability availability = doctorService.findByAvailabilityId(id);
        return AvailabilityRowMapper.toDtoWithDoctor(availability);
    }

    @GetMapping("/availabilities/search")
    @ResponseBody
    public List<AvailabilityDTO> searchAvailabilities(
            @RequestParam(required = false) String doctorName,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {

        LocalTime start = (startTime != null && !startTime.isBlank()) ? LocalTime.parse(startTime) : null;
        LocalTime end = (endTime != null && !endTime.isBlank()) ? LocalTime.parse(endTime) : null;

        return patientService.searchTodayAvailabilities(doctorName, start, end)
                            .stream()
                            .map(AvailabilityRowMapper::toDtoWithDoctor)
                            .collect(Collectors.toList());
    }

    @PostMapping("/appointment")
    @ResponseBody
    public ResponseEntity<?> bookAppointment(@AuthenticationPrincipal CustomUserDetails user,
                                            @RequestBody AppointmentDTO appointmentDTO) {
        try {
            Patient patient = (Patient) userService.findByEmail(user.getUsername());
            patientService.createAppointment(appointmentDTO, patient);
            notificationService.createNotification(patient, "Your appointment has been booked successfully.");
            return ResponseEntity.ok("Appointment booked successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to book appointment: " + e.getMessage());
        }
    }
    
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails user,
                           Model model) {
        PatientDTO patient = getPatient(user);
        PatientDTO patientProfile = getPatientProfile((Patient) userService.findByEmail(user.getUsername()));
        List<AppointmentDTO> appointments = patientService.getTop3AppointmentDTOsByPatient((Patient) userService.findByEmail(user.getUsername()));
        model.addAttribute("appointments", appointments);
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
        List<AppointmentDTO> appointments = patientService.getAppointmentDTOsByPatient((Patient) userService.findByEmail(user.getUsername()));
        model.addAttribute("current_user", patient);
        model.addAttribute("appointments", appointments);
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
