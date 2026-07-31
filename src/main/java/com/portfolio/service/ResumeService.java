package com.portfolio.service;

import com.portfolio.dto.ResumeResponse;
import com.portfolio.entity.Resume;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final FileUploadService fileUploadService;

    public ResumeService(ResumeRepository resumeRepository, FileUploadService fileUploadService) {
        this.resumeRepository = resumeRepository;
        this.fileUploadService = fileUploadService;
    }

    // Get the active resume
    @Transactional(readOnly = true)
    public ResumeResponse getActiveResume() {
        Resume resume = resumeRepository.findFirstByOrderByUploadDateDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No active resume uploaded yet."));
        return mapToResponse(resume);
    }

    // Upload new resume (replaces existing active resume)
    @Transactional
    public ResumeResponse uploadResume(MultipartFile file) {
        // Clean up previous resume if it exists
        Optional<Resume> existingResumeOpt = resumeRepository.findFirstByOrderByUploadDateDesc();
        if (existingResumeOpt.isPresent()) {
            Resume existingResume = existingResumeOpt.get();
            fileUploadService.deleteFileByUrl(existingResume.getFilePath());
            resumeRepository.delete(existingResume);
        }

        // Store new PDF
        String fileUrl = fileUploadService.uploadPdf(file);
        
        Resume newResume = Resume.builder()
                .fileName(file.getOriginalFilename())
                .filePath(fileUrl)
                .uploadDate(LocalDateTime.now())
                .build();
        
        Resume savedResume = resumeRepository.save(newResume);
        return mapToResponse(savedResume);
    }

    // Delete active resume
    @Transactional
    public void deleteActiveResume() {
        Resume resume = resumeRepository.findFirstByOrderByUploadDateDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No active resume found to delete."));
        
        // Delete physical file
        fileUploadService.deleteFileByUrl(resume.getFilePath());
        
        // Delete from database
        resumeRepository.delete(resume);
    }

    private ResumeResponse mapToResponse(Resume resume) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .fileUrl(resume.getFilePath())
                .uploadDate(resume.getUploadDate())
                .build();
    }
}
