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

    public static AdminDTO toDto(Admin admin){
        if(admin == null) return null;
        AdminDTO adminDto = new AdminDTO();
        adminDto.setId(admin.getId());
        adminDto.setFirstName(admin.getFirstName());
        adminDto.setMiddleName(admin.getMiddleName());
        adminDto.setLastName(admin.getLastName());
        adminDto.setEmail(admin.getEmail());
        adminDto.setPassword(admin.getPassword());
        adminDto.setAddress(admin.getAddress());
        adminDto.setAge(admin.getAge());
        adminDto.setRole(admin.getRole());
        adminDto.setProfilePath(admin.getProfilePath());
        return adminDto;
    }

    public static AdminDTO toDtoWithoutPassword(Admin admin){
        if(admin == null) return null;
        AdminDTO adminDto = new AdminDTO();
        adminDto.setId(admin.getId());
        adminDto.setFirstName(admin.getFirstName());
        adminDto.setMiddleName(admin.getMiddleName());
        adminDto.setLastName(admin.getLastName());
        adminDto.setEmail(admin.getEmail());
        adminDto.setAddress(admin.getAddress());
        adminDto.setAge(admin.getAge());
        adminDto.setRole(admin.getRole());
        adminDto.setProfilePath(admin.getProfilePath());
        return adminDto;
    }
}
