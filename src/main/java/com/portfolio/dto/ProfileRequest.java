package com.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String designation;

    private String about;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;

    private String location;

    private String profileImage;

    private String backgroundImage;

    private String github;

    private String linkedin;

    private String portfolio;

    private String twitter;

    private String instagram;

    private Boolean availableForHire;
}
