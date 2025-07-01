package com.cityStar.rowmapper;

import com.cityStar.dto.AppointmentDTO;
import com.cityStar.model.Appointment;
import com.cityStar.model.Availability;
import com.cityStar.model.Patient;

public class AppointmentRowMapper {
    
    public static Appointment toEntity(AppointmentDTO dto, 
                                       Patient patient, 
                                       Availability availability) {
        return Appointment.builder()
                .appointmentTime(dto.getAppointmentTime())
                .status(dto.getStatus())
                .availability(availability)
                .patient(patient)
                .build();
    }

    public static AppointmentDTO toDto(Appointment appointment) {
        return AppointmentDTO.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentInfo(appointment.getAppointmentInfo())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .availability(AvailabilityRowMapper.toDtoWithDoctor(appointment.getAvailability()))
                .patient(PatientRowMapper.toDtoWithoutPassword(appointment.getPatient())) // You can add a PatientDTO if needed
                .build();
    }
}
