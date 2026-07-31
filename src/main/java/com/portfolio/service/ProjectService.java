package com.portfolio.service;

import com.portfolio.dto.ProjectRequest;
import com.portfolio.dto.ProjectResponse;
import com.portfolio.entity.Project;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Get all projects sorted by display order
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Create project
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = Project.builder()
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .detailedDescription(request.getDetailedDescription())
                .technologies(request.getTechnologies())
                .githubUrl(request.getGithubUrl())
                .liveDemoUrl(request.getLiveDemoUrl())
                .image(request.getImage())
                .featured(request.getFeatured())
                .status(request.getStatus())
                .displayOrder(request.getDisplayOrder())
                .build();
        Project savedProject = projectRepository.save(project);
        return mapToResponse(savedProject);
    }

    // Update project
    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));

        project.setTitle(request.getTitle());
        project.setShortDescription(request.getShortDescription());
        project.setDetailedDescription(request.getDetailedDescription());
        project.setTechnologies(request.getTechnologies());
        project.setGithubUrl(request.getGithubUrl());
        project.setLiveDemoUrl(request.getLiveDemoUrl());
        project.setImage(request.getImage());
        project.setFeatured(request.getFeatured());
        project.setStatus(request.getStatus());
        project.setDisplayOrder(request.getDisplayOrder());

        Project updatedProject = projectRepository.save(project);
        return mapToResponse(updatedProject);
    }

    // Delete project
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with ID: " + id);
        }
        projectRepository.deleteById(id);
    }

    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .shortDescription(project.getShortDescription())
                .detailedDescription(project.getDetailedDescription())
                .technologies(project.getTechnologies())
                .githubUrl(project.getGithubUrl())
                .liveDemoUrl(project.getLiveDemoUrl())
                .image(project.getImage())
                .featured(project.getFeatured())
                .status(project.getStatus())
                .displayOrder(project.getDisplayOrder())
                .build();
    }
}
