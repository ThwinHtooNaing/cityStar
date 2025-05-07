package com.cityStar.rowmapper;

import com.cityStar.dto.PatientDTO;
import com.cityStar.model.Patient;

public class PatientRowMapper {
    public static Patient toEntity(PatientDTO patientDTO) {
        if (patientDTO == null) return null;
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setMiddleName(patientDTO.getMiddleName());
        patient.setLastName(patientDTO.getLastName());
        patient.setEmail(patientDTO.getEmail());
        patient.setPassword(patientDTO.getPassword());
        patient.setAddress(patientDTO.getAddress());
        patient.setAge(patientDTO.getAge());
        patient.setRole(patientDTO.getRole());
        patient.setProfilePath(patientDTO.getProfilePath());
        return patient;
    }
}
