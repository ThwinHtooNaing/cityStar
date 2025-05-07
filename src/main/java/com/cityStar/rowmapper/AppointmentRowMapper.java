package com.cityStar.rowmapper;

import com.cityStar.dto.AppointmentDTO;
import com.cityStar.model.Appointment;
import com.cityStar.model.Doctor;
import com.cityStar.model.Patient;

public class AppointmentRowMapper {
    
    public static Appointment toEntity(AppointmentDTO dto, Patient patient, Doctor doctor) {
        return Appointment.builder()
                .appointmentTime(dto.getAppointmentTime())
                .status(dto.getStatus())
                .patient(patient)
                .build();
    }
}
