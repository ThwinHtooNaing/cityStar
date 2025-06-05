package com.cityStar.service;


import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.cityStar.model.Availability;
import com.cityStar.repository.IavailabilityRepository;

@Service
public class DoctorService {

    private final IavailabilityRepository availabilityrepo;
    public DoctorService(IavailabilityRepository availabilityrepo) {
        this.availabilityrepo = availabilityrepo;
    }

    public void addAvailability(Availability availability) {
        availabilityrepo.save(availability);
    }

    public Availability getLatestAvailabilityForToday() {
        LocalDate today = LocalDate.now();
        return availabilityrepo.findTopByAvailableDateOrderByAvailabilityIdDesc(today)
                .orElse(null); // Or throw custom exception
    }
    
}
