package com.cityStar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminDTO extends UserDTO {
    public AdminDTO(String firstName,String lastName,String profilePath){
        super(firstName,lastName,profilePath);
    }
}
