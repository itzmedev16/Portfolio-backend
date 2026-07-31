package com.portfolio.service;

import com.portfolio.dto.ExperienceRequest;
import com.portfolio.dto.ExperienceResponse;
import com.portfolio.entity.Experience;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.ExperienceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    // Get enabled experiences for the public site
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getEnabledExperiences() {
        return experienceRepository.findAllByEnabledTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get all experiences for the admin panel
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getAllExperiences() {
        return experienceRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Create experience
    @Transactional
    public ExperienceResponse createExperience(ExperienceRequest request) {
        Experience experience = Experience.builder()
                .title(request.getTitle())
                .organization(request.getOrganization())
                .duration(request.getDuration())
                .description(request.getDescription())
                .type(request.getType())
                .displayOrder(request.getDisplayOrder())
                .enabled(request.getEnabled())
                .build();
        Experience savedExperience = experienceRepository.save(experience);
        return mapToResponse(savedExperience);
    }

    // Update experience
    @Transactional
    public ExperienceResponse updateExperience(Long id, ExperienceRequest request) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with ID: " + id));

        experience.setTitle(request.getTitle());
        experience.setOrganization(request.getOrganization());
        experience.setDuration(request.getDuration());
        experience.setDescription(request.getDescription());
        experience.setType(request.getType());
        experience.setDisplayOrder(request.getDisplayOrder());
        experience.setEnabled(request.getEnabled());

        Experience updatedExperience = experienceRepository.save(experience);
        return mapToResponse(updatedExperience);
    }

    // Delete experience
    @Transactional
    public void deleteExperience(Long id) {
        if (!experienceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Experience not found with ID: " + id);
        }
        experienceRepository.deleteById(id);
    }

    private ExperienceResponse mapToResponse(Experience experience) {
        return ExperienceResponse.builder()
                .id(experience.getId())
                .title(experience.getTitle())
                .organization(experience.getOrganization())
                .duration(experience.getDuration())
                .description(experience.getDescription())
                .type(experience.getType())
                .displayOrder(experience.getDisplayOrder())
                .enabled(experience.getEnabled())
                .build();
    }
}
