package com.cityStar.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Patient extends User {

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();
}