package com.portfolio.service;

import com.portfolio.dto.SkillRequest;
import com.portfolio.dto.SkillResponse;
import com.portfolio.entity.Skill;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    // Get enabled skills for public site
    @Transactional(readOnly = true)
    public List<SkillResponse> getEnabledSkills() {
        return skillRepository.findAllByEnabledTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get all skills for admin panel
    @Transactional(readOnly = true)
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Create skill
    @Transactional
    public SkillResponse createSkill(SkillRequest request) {
        Skill skill = Skill.builder()
                .skillName(request.getSkillName())
                .category(request.getCategory())
                .percentage(request.getPercentage())
                .icon(request.getIcon())
                .displayOrder(request.getDisplayOrder())
                .enabled(request.getEnabled())
                .build();
        Skill savedSkill = skillRepository.save(skill);
        return mapToResponse(savedSkill);
    }

    // Update skill
    @Transactional
    public SkillResponse updateSkill(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with ID: " + id));

        skill.setSkillName(request.getSkillName());
        skill.setCategory(request.getCategory());
        skill.setPercentage(request.getPercentage());
        skill.setIcon(request.getIcon());
        skill.setDisplayOrder(request.getDisplayOrder());
        skill.setEnabled(request.getEnabled());

        Skill updatedSkill = skillRepository.save(skill);
        return mapToResponse(updatedSkill);
    }

    // Delete skill
    @Transactional
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with ID: " + id);
        }
        skillRepository.deleteById(id);
    }

    private SkillResponse mapToResponse(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .category(skill.getCategory())
                .percentage(skill.getPercentage())
                .icon(skill.getIcon())
                .displayOrder(skill.getDisplayOrder())
                .enabled(skill.getEnabled())
                .build();
    }
}
