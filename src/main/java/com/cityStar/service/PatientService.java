package com.cityStar.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cityStar.dto.AppointmentDTO;
import com.cityStar.dto.PatientDTO;
import com.cityStar.enums.Status;
import com.cityStar.model.Appointment;
import com.cityStar.model.Availability;
import com.cityStar.model.Patient;
import com.cityStar.repository.IappointmentRepository;
import com.cityStar.repository.IavailabilityRepository;
import com.cityStar.repository.IpatientRepository;
import com.cityStar.repository.IuserRepository;

@Service
public class PatientService {
    
    private final IuserRepository userRepository;
    private final StorageService storageService;
    private final IpatientRepository patientRepository;
    private final IappointmentRepository appointmentRepository;
    private final IavailabilityRepository availabilityRepository;

    public PatientService(IuserRepository userRepository,
                          StorageService storageService,
                          IpatientRepository patientRepository,
                          IappointmentRepository appointmentRepository,
                          IavailabilityRepository availabilityRepository) {
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public void updatePatientProfile(String email,
                                     PatientDTO dto,
                                     MultipartFile file) {
        
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (dto != null) {
            if (dto.getFirstName() != null) patient.setFirstName(dto.getFirstName());
            if (dto.getMiddleName() != null) patient.setMiddleName(dto.getMiddleName());
            if (dto.getLastName() != null) patient.setLastName(dto.getLastName());
            if (dto.getEmail() != null) patient.setEmail(dto.getEmail());
            if (dto.getAge() != null) patient.setAge(dto.getAge());
            if (dto.getAddress() != null) patient.setAddress(dto.getAddress());
        }

        if (file != null && !file.isEmpty()) {
            String imagePath = storageService.saveImg(patient, file);
            patient.setProfilePath(imagePath);
        }

        userRepository.save(patient);
    }

    public void createAppointment(AppointmentDTO dto,
                                  Patient patient) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentInfo(dto.getAppointmentInfo());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        Availability availability = availabilityRepository.findById(
            dto.getAvailability().getAvailabilityId())
            .orElseThrow(() -> new RuntimeException("Availability not found"));
        appointment.setAvailability(availability);
        appointment.setPatient(patient);
        appointment.setStatus(Status.Pending);
        appointmentRepository.save(appointment);
        availability.setIsAvailable(false);
        availabilityRepository.save(availability);
    }

    public List<Availability> searchTodayAvailabilities(String doctorName, 
                                                        LocalTime start, 
                                                        LocalTime end) {
        LocalDate today = LocalDate.now();
        String searchName = (doctorName != null && !doctorName.isBlank()) ? doctorName.toLowerCase() : null;

        List<Availability> todayAvailabilities = availabilityRepository.findByAvailableDate(today);

        return todayAvailabilities.stream()
            .filter(a -> (searchName == null || 
                        (a.getDoctor().getFirstName() + " " + a.getDoctor().getLastName()).toLowerCase().contains(searchName)) &&
                        (start == null || !a.getStartTime().isBefore(start)) &&
                        (end == null || !a.getEndTime().isAfter(end)))
            .collect(Collectors.toList());
    }
    
}
