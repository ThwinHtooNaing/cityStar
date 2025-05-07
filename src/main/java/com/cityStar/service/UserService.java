package com.cityStar.service;

import org.springframework.stereotype.Service;

import com.cityStar.exception.UserNotFoundException;
import com.cityStar.model.User;
import com.cityStar.repository.IuserRepository;

@Service
public class UserService {
    
    private final IuserRepository userRepository;
    public UserService(IuserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found with email: " + email));
    }
    
}
