package com.cityStar.dto;

import com.cityStar.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private Long id;

    @NotBlank(message = "First name is required!")
    @Size(max = 50, message = "Not More than 50 characters!")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required!")
    @Size(max = 50, message = "Not More than 50 characters!")
    private String lastName;

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format!!")
    private String email;

    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/~`]).{8,20}$",
        message = "one capital and one special character with 8 words!"
    )
    private String password;

    @NotBlank(message = "Address is required!")
    @Size(max = 100, message = "Address must be a max 100 characters!")
    private String address;

    @NotNull(message = "Age is required!")
    @Positive(message = "Age must be a positive numbe!")
    private Integer age;

    private Role role;

    private String profilePath;

    public UserDTO(String firstName,String lastName,String profilePath){
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePath = profilePath;
    }
}