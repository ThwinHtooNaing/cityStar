package com.cityStar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.model.Admin;

public interface IadminRepository extends JpaRepository<Admin,Long> {
    
} 
