package com.cityStar.rowmapper;

import com.cityStar.dto.AdminDTO;
import com.cityStar.model.Admin;

public class AdminRowMapper {
    public static Admin toEntity(AdminDTO dto){
        if(dto == null) return null;
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setFirstName(dto.getFirstName());
        admin.setMiddleName(dto.getMiddleName());
        admin.setLastName(dto.getLastName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());
        admin.setAddress(dto.getAddress());
        admin.setAge(dto.getAge());
        admin.setRole(dto.getRole());
        admin.setProfilePath(dto.getProfilePath());
        return admin;
    }
}
