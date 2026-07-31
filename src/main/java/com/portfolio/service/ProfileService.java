package com.portfolio.service;

import com.portfolio.dto.ProfileRequest;
import com.portfolio.dto.ProfileResponse;
import com.portfolio.entity.Profile;
import com.portfolio.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // Retrieve profile
    @Transactional
    public ProfileResponse getProfile() {
        Profile profile = profileRepository.findFirstByOrderByIdAsc()
                .orElseGet(this::createDefaultProfile);
        return mapToResponse(profile);
    }

    // Update profile
    @Transactional
    public ProfileResponse updateProfile(ProfileRequest request) {
        Profile profile = profileRepository.findFirstByOrderByIdAsc()
                .orElseGet(() -> Profile.builder().build());

        profile.setFullName(request.getFullName());
        profile.setDesignation(request.getDesignation());
        profile.setAbout(request.getAbout());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setLocation(request.getLocation());
        profile.setProfileImage(request.getProfileImage());
        profile.setBackgroundImage(request.getBackgroundImage());
        profile.setGithub(request.getGithub());
        profile.setLinkedin(request.getLinkedin());
        profile.setPortfolio(request.getPortfolio());
        profile.setTwitter(request.getTwitter());
        profile.setInstagram(request.getInstagram());
        profile.setAvailableForHire(request.getAvailableForHire());

        Profile updatedProfile = profileRepository.save(profile);
        return mapToResponse(updatedProfile);
    }


    private Profile createDefaultProfile() {
        Profile profile = Profile.builder()
                .fullName("Devoff Itzme")
                .designation("Senior Java Full Stack Developer & Architect")
                .about("Passionate software engineer with 6+ years of experience designing and developing scalable REST APIs, microservices, and enterprise web solutions using Spring Boot, Angular, and React.")
                .email("your.email@example.com")
                .phone("+91 98765 43210")
                .location("Bangalore, India")
                .profileImage("/uploads/profile_default.png")
                .backgroundImage("/uploads/bg_default.png")
                .github("https://github.com/itzmedev")
                .linkedin("https://linkedin.com/in/itzmedev")
                .portfolio("https://itzmedev.com")
                .twitter("https://twitter.com/itzmedev")
                .instagram("https://instagram.com/itzmedev")
                .availableForHire(true)
                .build();
        return profileRepository.save(profile);
    }

    private ProfileResponse mapToResponse(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .fullName(profile.getFullName())
                .designation(profile.getDesignation())
                .about(profile.getAbout())
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .location(profile.getLocation())
                .profileImage(profile.getProfileImage())
                .backgroundImage(profile.getBackgroundImage())
                .github(profile.getGithub())
                .linkedin(profile.getLinkedin())
                .portfolio(profile.getPortfolio())
                .twitter(profile.getTwitter())
                .instagram(profile.getInstagram())
                .availableForHire(profile.getAvailableForHire())
                .build();
    }
}
