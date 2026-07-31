package com.portfolio.controller;

import com.portfolio.dto.*;
import com.portfolio.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProfileService profileService;
    private final SkillService skillService;
    private final ProjectService projectService;
    private final CertificateService certificateService;
    private final ResumeService resumeService;
    private final ContactMessageService messageService;
    private final DashboardService dashboardService;
    private final FileUploadService fileUploadService;
    private final ExperienceService experienceService;

    public AdminController(ProfileService profileService,
                           SkillService skillService,
                           ProjectService projectService,
                           CertificateService certificateService,
                           ResumeService resumeService,
                           ContactMessageService messageService,
                           DashboardService dashboardService,
                           FileUploadService fileUploadService,
                           ExperienceService experienceService) {
        this.profileService = profileService;
        this.skillService = skillService;
        this.projectService = projectService;
        this.certificateService = certificateService;
        this.resumeService = resumeService;
        this.messageService = messageService;
        this.dashboardService = dashboardService;
        this.fileUploadService = fileUploadService;
        this.experienceService = experienceService;
    }

    // ==========================================
    // Profile Management
    // ==========================================
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getAdminProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileResponse> updateAdminProfile(@Valid @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(request));
    }

    // ==========================================
    // Experience Management
    // ==========================================
    @GetMapping("/experiences")
    public ResponseEntity<List<ExperienceResponse>> getAllExperiences() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @PostMapping("/experiences")
    public ResponseEntity<ExperienceResponse> createExperience(@Valid @RequestBody ExperienceRequest request) {
        ExperienceResponse response = experienceService.createExperience(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/experiences/{id}")
    public ResponseEntity<ExperienceResponse> updateExperience(@PathVariable Long id, @Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.ok(experienceService.updateExperience(id, request));
    }

    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // Skills Management
    // ==========================================
    @GetMapping("/skills")
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @PostMapping("/skills")
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody SkillRequest request) {
        SkillResponse response = skillService.createSkill(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(skillService.updateSkill(id, request));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // Projects Management
    // ==========================================
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
        ProjectResponse response = projectService.createProject(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // Certificates Management
    // ==========================================
    @GetMapping("/certificates")
    public ResponseEntity<List<CertificateResponse>> getAllCertificates() {
        return ResponseEntity.ok(certificateService.getAllCertificates());
    }

    @PostMapping("/certificates")
    public ResponseEntity<CertificateResponse> createCertificate(@Valid @RequestBody CertificateRequest request) {
        CertificateResponse response = certificateService.createCertificate(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/certificates/{id}")
    public ResponseEntity<CertificateResponse> updateCertificate(@PathVariable Long id, @Valid @RequestBody CertificateRequest request) {
        return ResponseEntity.ok(certificateService.updateCertificate(id, request));
    }

    @DeleteMapping("/certificates/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // Resume Management
    // ==========================================
    @GetMapping("/resume")
    public ResponseEntity<ResumeResponse> getAdminResume() {
        return ResponseEntity.ok(resumeService.getActiveResume());
    }

    @PostMapping("/resume")
    public ResponseEntity<ResumeResponse> uploadResume(@RequestParam("file") MultipartFile file) {
        ResumeResponse response = resumeService.uploadResume(file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/resume")
    public ResponseEntity<Void> deleteResume() {
        resumeService.deleteActiveResume();
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // Message Inbox Management
    // ==========================================
    @GetMapping("/messages")
    public ResponseEntity<List<ContactMessageResponse>> getMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PutMapping("/messages/{id}/read")
    public ResponseEntity<ContactMessageResponse> markMessageAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.markAsRead(id));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // Dashboard Stats
    // ==========================================
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    // General File Upload Endpoint
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileUrl = fileUploadService.uploadImage(file);
        System.out.println("Returned URL : "+fileUrl);
        Map<String, String> response = new HashMap<>();
        response.put("fileUrl", fileUrl);
        return ResponseEntity.ok(response);
    }
}
