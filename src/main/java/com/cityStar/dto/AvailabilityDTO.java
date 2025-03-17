package com.cityStar.dto;

import java.time.LocalDate;
import java.time.LocalTime;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailabilityDTO {
    private Long availabilityId;
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
}

