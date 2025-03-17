package com.cityStar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityStar.model.User;

public interface IuserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email); 
} 
