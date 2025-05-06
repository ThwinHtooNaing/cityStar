package com.cityStar;

import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cityStar.enums.Role;
import com.cityStar.model.Admin;
import com.cityStar.repository.IuserRepository;

@SpringBootApplication
public class PatientDoctorSystemApplication {

    private final Logger logger = Logger.getLogger(PatientDoctorSystemApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(PatientDoctorSystemApplication.class, args);
	}

	@Bean
    public CommandLineRunner initAdminUser(IuserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("root@gmail.com").isEmpty()) {
                Admin admin = new Admin();
                admin.setEmail("root@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));  
                admin.setRole(Role.ADMIN); 
                userRepository.save(admin);
                logger.info("admin is created completely");
            }
        };
    }

}
