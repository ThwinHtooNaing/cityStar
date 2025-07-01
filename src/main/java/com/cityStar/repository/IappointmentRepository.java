package com.cityStar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.model.Appointment;
import com.cityStar.model.Patient;

public interface IappointmentRepository extends JpaRepository<Appointment, Long>{
    List<Appointment> findByPatient(Patient patient);
}