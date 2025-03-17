package com.cityStar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.model.Doctor;

public interface IdoctorRepository extends JpaRepository<Doctor, Long> {
}
