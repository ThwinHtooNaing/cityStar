package com.cityStar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cityStar.enums.Role;
import com.cityStar.model.Admin;
import com.cityStar.repository.IuserRepository;

import java.util.logging.Logger;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final IuserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(AdminInitializer.class.getName());

    public AdminInitializer(IuserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("root@gmail.com").isEmpty()) {
            Admin admin = new Admin();
            admin.setFirstName("admin");
            admin.setProfilePath("/img/default_profile_photo.jpg");
            admin.setEmail("root@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            logger.info("Admin user created successfully.");
        }
    }
}
