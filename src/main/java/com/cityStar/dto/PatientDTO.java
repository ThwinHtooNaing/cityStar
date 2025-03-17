package com.cityStar.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO extends UserDTO {
    private List<AppointmentDTO> appointments;
}
