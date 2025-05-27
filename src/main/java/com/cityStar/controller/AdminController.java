package com.cityStar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cityStar.dto.AdminDTO;
import com.cityStar.model.Admin;
import com.cityStar.rowmapper.AdminRowMapper;
import com.cityStar.security.CustomUserDetails;
import com.cityStar.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String Dashboard(@AuthenticationPrincipal CustomUserDetails user,
                            Model model) {
        AdminDTO admin = getAdmin(user);
        model.addAttribute("current_user", admin);
        return "admin/admin-dashboard";
    }

    @GetMapping("/profile")
    public String Profile(@AuthenticationPrincipal CustomUserDetails user,
                            Model model) {
        AdminDTO admin = getAdmin(user);
        AdminDTO adminProfile = getAdminProfile((Admin) userService.findByEmail(user.getUsername()));
        model.addAttribute("admin_profile", adminProfile);
        model.addAttribute("current_user", admin);
        return "admin/admin-profile";
    }

    @GetMapping("/users")
    public String Users(@AuthenticationPrincipal CustomUserDetails user,
                            Model model) {
        AdminDTO admin = getAdmin(user);
        model.addAttribute("current_user", admin);
        return "admin/admin-users";
    }

    @GetMapping("/appointments")
    public String Appointments(@AuthenticationPrincipal CustomUserDetails user,
                            Model model) {
        AdminDTO admin = getAdmin(user);
        model.addAttribute("current_user", admin);
        return "admin/admin-appointments";
    }

    private AdminDTO getAdmin(CustomUserDetails user){
        Admin admin = (Admin) userService.findByEmail(user.getUsername());
        return new AdminDTO(admin.getFirstName(), admin.getLastName(), admin.getProfilePath());
    }

    private AdminDTO getAdminProfile(Admin admin){
        return AdminRowMapper.toDtoWithoutPassword(admin);
    }
}
