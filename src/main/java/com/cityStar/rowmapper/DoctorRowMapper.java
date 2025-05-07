package com.cityStar.rowmapper;

import com.cityStar.dto.DoctorDTO;
import com.cityStar.model.Doctor;

public class DoctorRowMapper {
    
    public static Doctor toEntity(DoctorDTO dto) {
        if (dto == null) return null;
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setFirstName(dto.getFirstName());
        doctor.setMiddleName(dto.getMiddleName());
        doctor.setLastName(dto.getLastName());
        doctor.setEmail(dto.getEmail());
        doctor.setPassword(dto.getPassword());
        doctor.setAddress(dto.getAddress());
        doctor.setAge(dto.getAge());
        doctor.setRole(dto.getRole());
        doctor.setProfilePath(dto.getProfilePath());
        doctor.setBio(dto.getBio());
        doctor.setContactInfo(dto.getContactInfo());
        doctor.setSpeciality(dto.getSpecialty());
        return doctor;
    }
}
