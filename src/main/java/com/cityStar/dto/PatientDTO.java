package com.cityStar.dto;

import java.util.List;

import com.cityStar.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO extends UserDTO {
    private List<AppointmentDTO> appointments;
    public PatientDTO(Long id,String firstName, String middleName, String lastName, String email, String password, String address, Integer age, Role role, String profilePath) {
        super(id,firstName, middleName, lastName, email, password, address, age, role, profilePath);
    }

    public PatientDTO(String firstName,String lastName,String profilePath){
        super(firstName,lastName,profilePath);
    }
}
