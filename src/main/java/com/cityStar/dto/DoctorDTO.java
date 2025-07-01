package com.cityStar.dto;

import java.util.List;

import com.cityStar.enums.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDTO extends UserDTO {
    private String bio;

    @NotBlank
    private String contactInfo;

    @NotBlank
    private String speciality;
    
    private List<AvailabilityDTO> availabilities;

    public DoctorDTO(Long id,String firstName, String middleName, String lastName, String email, String password, String address, Integer age, Role role, String profilePath, String bio, String contactInfo, String speciality) {
        super(id,firstName, middleName, lastName, email, password, address, age, role, profilePath);
        this.bio = bio;
        this.contactInfo = contactInfo;
        this.speciality = speciality;
    }

    public DoctorDTO(String firstName,String lastName,String profilePath){
        super(firstName,lastName,profilePath);
    }

    public DoctorDTO(String firstName,String lastName,String profilePath,String speciality){
        super(firstName,lastName,profilePath);
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "firstName='" + getFirstName() + '\'' +
                ", middleName='" + getMiddleName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", age=" + getAge() +
                ", contactInfo='" + contactInfo + '\'' +
                ", speciality='" + speciality + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
