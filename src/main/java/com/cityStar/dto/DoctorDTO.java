package com.cityStar.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO extends UserDTO {
    private String bio;
    private String contactInfo;
    private String specialty;
    private List<AvailabilityDTO> availabilities;
}
