package com.cityStar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {
    // No extra fields needed
}