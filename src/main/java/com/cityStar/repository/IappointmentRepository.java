package com.cityStar.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.enums.Status;
import com.cityStar.model.Appointment;
import com.cityStar.model.Doctor;
import com.cityStar.model.Patient;

public interface IappointmentRepository extends JpaRepository<Appointment, Long>{
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByAvailability_DoctorAndStatus(Doctor doctor, Status status);
    List<Appointment> findByAvailability_Doctor(Doctor doctor);

    List<Appointment> findTop3ByAvailability_DoctorAndStatusAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(
    Doctor doctor, Status status, LocalDateTime start, LocalDateTime end
    );

    List<Appointment> findByStatus(Status status);

    long countByAvailability_Doctor(Doctor doctor);

    long countByAvailability_DoctorAndStatus(Doctor doctor, Status status);
}