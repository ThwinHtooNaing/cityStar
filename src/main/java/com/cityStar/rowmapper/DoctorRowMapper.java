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
        doctor.setSpeciality(dto.getSpeciality());
        return doctor;
    }

    public static DoctorDTO toDtoWithoutPassword(Doctor doctor){
        if(doctor == null) return null;
        DoctorDTO doctorDto = new DoctorDTO();
        doctorDto.setId(doctor.getId());
        doctorDto.setFirstName(doctor.getFirstName());
        doctorDto.setMiddleName(doctor.getMiddleName());
        doctorDto.setLastName(doctor.getLastName());
        doctorDto.setEmail(doctor.getEmail());
        doctorDto.setAddress(doctor.getAddress());
        doctorDto.setAge(doctor.getAge());
        doctorDto.setRole(doctor.getRole());
        doctorDto.setProfilePath(doctor.getProfilePath());
        doctorDto.setBio(doctor.getBio());
        doctorDto.setContactInfo(doctor.getContactInfo());
        doctorDto.setSpeciality(doctor.getSpeciality());
        return doctorDto;
    }
}
