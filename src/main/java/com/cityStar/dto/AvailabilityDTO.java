package com.cityStar.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilityDTO {
    private Long availabilityId;
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private DoctorDTO doctor; 
}