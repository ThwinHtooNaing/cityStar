package com.cityStar.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cityStar.dto.PatientDTO;
import com.cityStar.model.Patient;
import com.cityStar.repository.IappointmentRepository;
import com.cityStar.repository.IpatientRepository;
import com.cityStar.repository.IuserRepository;

@Service
public class PatientService {
    
    private final IuserRepository userRepository;
    private final StorageService storageService;
    private final IpatientRepository patientRepository;
    private final IappointmentRepository appointmentRepository;

    public PatientService(IuserRepository userRepository,
                          StorageService storageService,
                          IpatientRepository patientRepository,
                          IappointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
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
}
