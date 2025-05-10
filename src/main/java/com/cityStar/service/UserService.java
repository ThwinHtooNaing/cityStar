package com.cityStar.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cityStar.exception.EmailDuplicationException;
import com.cityStar.model.User;
import com.cityStar.repository.IuserRepository;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    
    private final IuserRepository userRepository;
    public UserService(IuserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user){
        boolean isUserExit = userRepository.findByEmail(user.getEmail()).isPresent();
        if(!isUserExit){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getProfilePath() == null || user.getProfilePath().isEmpty()) {
                user.setProfilePath("/img/default_profile_photo.jpg"); 
            }
            userRepository.save(user);
        }else{
            throw new EmailDuplicationException("this emails does exists");
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }
    
}
