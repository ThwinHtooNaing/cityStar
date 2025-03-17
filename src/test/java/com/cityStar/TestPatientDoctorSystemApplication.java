package com.cityStar;

import org.springframework.boot.SpringApplication;

public class TestPatientDoctorSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(PatientDoctorSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
