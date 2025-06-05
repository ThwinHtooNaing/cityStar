package com.cityStar.service;


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
    
}
