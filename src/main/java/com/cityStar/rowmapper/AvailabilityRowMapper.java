package com.cityStar.rowmapper;

import com.cityStar.dto.AvailabilityDTO;
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
}
