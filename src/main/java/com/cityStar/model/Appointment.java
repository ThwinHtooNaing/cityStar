package com.cityStar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.cityStar.enums.Status;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String appointmentInfo;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;

    private LocalDateTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private Status status;
}