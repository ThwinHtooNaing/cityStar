package com.cityStar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cityStar.dto.AdminDTO;
import com.cityStar.security.CustomUserDetails;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String Dashboard(@AuthenticationPrincipal CustomUserDetails user,
                            Model model) {
        AdminDTO admin = getAdmin(user);
        model.addAttribute("current_user", admin);
        return "admin/admin-dashboard";
    }

    private AdminDTO getAdmin(CustomUserDetails user){
        return new AdminDTO(user.getFirstName(), user.getLastName(), user.getProfilePath());
        
    }
}
