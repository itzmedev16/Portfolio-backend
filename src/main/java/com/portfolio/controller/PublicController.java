package com.portfolio.controller;

import com.portfolio.dto.*;
import com.portfolio.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicController {

    private final ProfileService profileService;
    private final SkillService skillService;
    private final ProjectService projectService;
    private final CertificateService certificateService;
    private final ResumeService resumeService;
    private final ContactMessageService messageService;
    private final ExperienceService experienceService;

    public PublicController(ProfileService profileService,
                            SkillService skillService,
                            ProjectService projectService,
                            CertificateService certificateService,
                            ResumeService resumeService,
                            ContactMessageService messageService,
                            ExperienceService experienceService) {
        this.profileService = profileService;
        this.skillService = skillService;
        this.projectService = projectService;
        this.certificateService = certificateService;
        this.resumeService = resumeService;
        this.messageService = messageService;
        this.experienceService = experienceService;
    }

    // Get public experiences (Work + Education)
    @GetMapping("/experiences")
    public ResponseEntity<List<ExperienceResponse>> getExperiences() {
        return ResponseEntity.ok(experienceService.getEnabledExperiences());
    }

    // Get public profile
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    // Get active skills
    @GetMapping("/skills")
    public ResponseEntity<List<SkillResponse>> getSkills() {
        return ResponseEntity.ok(skillService.getEnabledSkills());
    }

    // Get projects
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // Get certificates
    @GetMapping("/certificates")
    public ResponseEntity<List<CertificateResponse>> getCertificates() {
        return ResponseEntity.ok(certificateService.getAllCertificates());
    }

    // Get active resume info
    @GetMapping("/resume")
    public ResponseEntity<ResumeResponse> getResume() {
        return ResponseEntity.ok(resumeService.getActiveResume());
    }

    // Submit contact message
    @PostMapping("/contact")
    public ResponseEntity<ContactMessageResponse> contact(@Valid @RequestBody ContactMessageRequest request) {
        ContactMessageResponse response = messageService.submitMessage(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
