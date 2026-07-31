package com.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private Long id;
    private String fullName;
    private String designation;
    private String about;
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
