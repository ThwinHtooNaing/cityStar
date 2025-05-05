package com.cityStar.dto;

import com.cityStar.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private Integer age;
    private Role role;
    private String profilePath;
}