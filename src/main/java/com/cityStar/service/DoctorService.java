package com.cityStar.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cityStar.dto.AppointmentDTO;
import com.cityStar.dto.AvailabilityDTO;
import com.cityStar.dto.DoctorDTO;
import com.cityStar.enums.Status;
import com.cityStar.model.Appointment;
import com.cityStar.model.Availability;
import com.cityStar.model.Doctor;
import com.cityStar.repository.IappointmentRepository;
import com.cityStar.repository.IavailabilityRepository;
import com.cityStar.repository.IdoctorRepository;
import com.cityStar.repository.IuserRepository;
import com.cityStar.rowmapper.AppointmentRowMapper;
import com.cityStar.rowmapper.AvailabilityRowMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class DoctorService {

    private final IavailabilityRepository availabilityrepo;
    private final IdoctorRepository doctorRepository;
    private final StorageService storageService;
    private final IuserRepository userRepository;
    private final IappointmentRepository appointmentRepository;
    public DoctorService(IavailabilityRepository availabilityrepo,
                         IdoctorRepository doctorRepository,
                         StorageService storageService,
                         IuserRepository userRepository,
                         IappointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.availabilityrepo = availabilityrepo;
        this.doctorRepository = doctorRepository;
        this.storageService = storageService;
        this.appointmentRepository = appointmentRepository;
    }

    public Availability findByAvailabilityId(Long id) {
        return availabilityrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found"));
    }

    public void addAvailability(Availability availability) {
        availability.setIsAvailable(true);
        availabilityrepo.save(availability);
    }

    public List<AvailabilityDTO> getAllAvailabilitiesForToday() {
        LocalDate today = LocalDate.now();
        List<Availability> availabilities = availabilityrepo.findByAvailableDateAndIsAvailableTrue(today);
        return availabilities.stream()
            .map(AvailabilityRowMapper::toDtoWithDoctor)
            .collect(Collectors.toList());    
    }

    public Availability getLatestAvailabilityForToday(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        LocalDate today = LocalDate.now();
        return availabilityrepo.findTopByAvailableDateAndDoctorOrderByAvailabilityIdDesc(today,     doctor).orElse(null);   
    }

    public void updateDoctorProfile(String email,
                                    DoctorDTO dto, 
                                    MultipartFile file) {
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
    
    public List<AppointmentDTO> getAppointmentsByDoctorAndStatus(Doctor doctor, 
                                                                 Status status) {
        return appointmentRepository.findByAvailability_DoctorAndStatus(doctor, status)
                .stream().map(AppointmentRowMapper::toDto).collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByStatus(Status status) {
        return appointmentRepository.findByStatus(status)
                .stream().map(AppointmentRowMapper::toDto).collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream().map(AppointmentRowMapper::toDto).collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAllAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findByAvailability_Doctor(doctor)
                .stream().map(AppointmentRowMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void updateStatus(Long appointmentId, Status status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        appointment.setStatus(status);
    }

    public Map<String, Long> getAppointmentCountsByStatusForDoctor(Doctor doctor) {
        long all = appointmentRepository.countByAvailability_Doctor(doctor);
        long pending = appointmentRepository.countByAvailability_DoctorAndStatus(doctor, Status.Pending);
        long confirmed = appointmentRepository.countByAvailability_DoctorAndStatus(doctor, Status.Confirmed);
        long cancelled = appointmentRepository.countByAvailability_DoctorAndStatus(doctor, Status.Cancelled);

        System.out.println("All: " + all);
        System.out.println("Pending: " + pending);
        System.out.println("Confirmed: " + confirmed);
        System.out.println("Cancelled: " + cancelled);

        Map<String, Long> counts = new HashMap<>();
        counts.put("all", all);
        counts.put("pending", pending);
        counts.put("confirmed", confirmed);
        counts.put("cancelled", cancelled);

        return counts;
    }

    public List<AppointmentDTO> getTopThreePendingTodayForDoctor(Doctor doctor) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        List<Appointment> appointments = appointmentRepository
            .findTop3ByAvailability_DoctorAndStatusAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(
                doctor, Status.Pending, startOfDay, endOfDay
            );

        return appointments.stream()
                .map(AppointmentRowMapper::toDto)
                .toList();
    }
}
