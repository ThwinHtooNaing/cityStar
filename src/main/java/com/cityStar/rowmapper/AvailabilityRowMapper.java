package com.cityStar.rowmapper;

import com.cityStar.dto.AvailabilityDTO;
import com.cityStar.dto.DoctorDTO;
import com.cityStar.model.Availability;
import com.cityStar.model.Doctor;

public class AvailabilityRowMapper {
    public static Availability toEntity(AvailabilityDTO dto, Doctor doctor) {
        return Availability.builder()
                .availableDate(dto.getAvailableDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .doctor(doctor)
                .build();
    }

    public static AvailabilityDTO toDto(Availability availability) {
        return AvailabilityDTO.builder()
                .availabilityId(availability.getAvailabilityId())
                .availableDate(availability.getAvailableDate())
                .startTime(availability.getStartTime())
                .endTime(availability.getEndTime())
                .build();
    }

    public static AvailabilityDTO toDtoWithDoctor(Availability availability) {
        Doctor doctor = availability.getDoctor();
        DoctorDTO doctorDTO = new DoctorDTO(
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getProfilePath(),
                doctor.getSpeciality()
        );

        return AvailabilityDTO.builder()
                .availabilityId(availability.getAvailabilityId())
                .availableDate(availability.getAvailableDate())
                .startTime(availability.getStartTime())
                .endTime(availability.getEndTime())
                .doctor(doctorDTO)
                .build();
    }
}
