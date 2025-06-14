package com.cityStar.service;


import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cityStar.dto.DoctorDTO;
import com.cityStar.model.Availability;
import com.cityStar.model.Doctor;
import com.cityStar.repository.IavailabilityRepository;
import com.cityStar.repository.IdoctorRepository;
import com.cityStar.repository.IuserRepository;

@Service
public class DoctorService {

    private final IavailabilityRepository availabilityrepo;
    private final IdoctorRepository doctorRepository;
    private final StorageService storageService;
    private final IuserRepository userRepository;
    public DoctorService(IavailabilityRepository availabilityrepo,
                         IdoctorRepository doctorRepository,
                         StorageService storageService,
                         IuserRepository userRepository) {
        this.userRepository = userRepository;
        this.availabilityrepo = availabilityrepo;
        this.doctorRepository = doctorRepository;
        this.storageService = storageService;
    }

    public void addAvailability(Availability availability) {
        availabilityrepo.save(availability);
    }

    public Availability getLatestAvailabilityForToday(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        LocalDate today = LocalDate.now();
        return availabilityrepo.findTopByAvailableDateAndDoctorOrderByAvailabilityIdDesc(today,     doctor).orElse(null);   
    }

    public void updateDoctorProfile(String email, DoctorDTO dto, MultipartFile file) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (dto != null) {
            if (dto.getFirstName() != null) doctor.setFirstName(dto.getFirstName());
            if (dto.getMiddleName() != null) doctor.setMiddleName(dto.getMiddleName());
            if (dto.getLastName() != null) doctor.setLastName(dto.getLastName());
            if (dto.getEmail() != null) doctor.setEmail(dto.getEmail());
            if (dto.getAge() != null) doctor.setAge(dto.getAge());
            if (dto.getContactInfo() != null) doctor.setContactInfo(dto.getContactInfo());
            if (dto.getSpeciality() != null) doctor.setSpeciality(dto.getSpeciality());
            if (dto.getBio() != null) doctor.setBio(dto.getBio());
        }

        if (file != null && !file.isEmpty()) {
            String imagePath = storageService.saveImg(doctor,file);
            doctor.setProfilePath(imagePath);
        }
        userRepository.save(doctor);
    }
    
}
