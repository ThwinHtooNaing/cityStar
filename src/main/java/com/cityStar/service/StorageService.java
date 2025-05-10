package com.cityStar.service;

import org.springframework.web.multipart.MultipartFile;

import com.cityStar.model.User;

public interface StorageService {
    public String saveImg(User user,MultipartFile file);
}
