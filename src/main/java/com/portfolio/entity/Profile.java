package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String designation;

    @Column(columnDefinition = "TEXT")
    private String about;

    private String email;

    private String phone;

    private String location;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "background_image")
    private String backgroundImage;

    private String github;

    private String linkedin;

    private String portfolio;

    private String twitter;

    private String instagram;

    @Column(name = "available_for_hire")
    private Boolean availableForHire;
}
