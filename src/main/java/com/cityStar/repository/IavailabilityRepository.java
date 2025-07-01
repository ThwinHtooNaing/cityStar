package com.cityStar.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.model.Availability;
import com.cityStar.model.Doctor;

public interface IavailabilityRepository extends JpaRepository<Availability,Long>{
    List<Availability> findByDoctorId(Long doctorId);
    List<Availability> findByAvailableDate(LocalDate date);
    List<Availability> findByAvailableDateAndIsAvailableTrue(LocalDate date);
    Optional<Availability> findTopByAvailableDateOrderByAvailabilityIdDesc(LocalDate Date);
    Optional<Availability> findTopByAvailableDateAndDoctorOrderByAvailabilityIdDesc(LocalDate availableDate, Doctor doctor);
}
