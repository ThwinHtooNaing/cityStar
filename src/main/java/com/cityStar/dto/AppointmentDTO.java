package com.cityStar.dto;
import com.cityStar.enums.Status;
import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDTO {
    private Long appointmentId;
    private PatientDTO patient;
    private AvailabilityDTO availability;
    private LocalDateTime appointmentTime;
    private Status status;
}
