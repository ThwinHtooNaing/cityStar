package com.cityStar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import org.springframework.lang.Nullable;

import com.cityStar.enums.Role;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String middleName;

    @Column(nullable = true)
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String profilePath;
}