package com.cityStar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.model.Patient;

public interface IpatientRepository extends JpaRepository<Patient,Long>{

} 
