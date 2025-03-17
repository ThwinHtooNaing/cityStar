package com.cityStar.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Doctor extends User {

    private String bio;
    private String contactInfo;
    private String specialty;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities = new ArrayList<>();
    
}