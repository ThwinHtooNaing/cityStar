package com.cityStar.dto;

import com.cityStar.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String address;
    private Integer age;
    private Role role;
}