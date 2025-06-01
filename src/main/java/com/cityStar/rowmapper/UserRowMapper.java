package com.cityStar.rowmapper;

import com.cityStar.dto.UserDTO;
import com.cityStar.model.User;

public class UserRowMapper {
    public static UserDTO toDtoWithoutPassword(User user) {
        if (user == null) return null;
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setAge(user.getAge());
        userDto.setRole(user.getRole());
        userDto.setProfilePath(user.getProfilePath());
        return userDto;
    }
}
