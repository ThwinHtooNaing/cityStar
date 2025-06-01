package com.cityStar.dto;
import com.cityStar.enums.Status;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long appointmentId;
    private String appointmentInfo;
    private AvailabilityDTO availability;
    private LocalDateTime appointmentTime;
    private Status status;
    private PatientDTO patient;
}
