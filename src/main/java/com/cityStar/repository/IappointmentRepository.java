package com.cityStar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.model.Appointment;

public interface IappointmentRepository extends JpaRepository<Appointment, Long>{
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByAvailabilityDoctorId(Long doctorId);
}