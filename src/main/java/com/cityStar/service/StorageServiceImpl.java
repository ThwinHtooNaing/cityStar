package com.cityStar.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cityStar.model.User;

@Service
public class StorageServiceImpl implements StorageService{

    private final String BASE_UPLOAD_PATH = "src/main/resources/static/img/profiles/";
    @Override
    public String saveImg(User user, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty or null");
        }
        try {

            String sanitizedName = (user.getFirstName()+"_"+user.getLastName()+"_").toLowerCase().replaceAll("\\s", "");
            String UPLOAD_DIR = BASE_UPLOAD_PATH+"/"+user.getRole().toString().toLowerCase()+
            "/";
            Path userDir = Paths.get(UPLOAD_DIR+sanitizedName);
            Files.createDirectories(userDir);
            
            String fileName = UUID.randomUUID()+"_"+LocalDate.now().toString();
            Path filePath = userDir.resolve(fileName);
            Files.write(filePath,file.getBytes());
            return "/img/profiles/"+user.getRole().toString().toLowerCase()+"/"+sanitizedName+"/"+fileName;
        } catch (IOException e){
            throw new RuntimeException("Error saving profile picture", e);
        }

    }
    
}
