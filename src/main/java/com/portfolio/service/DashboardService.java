package com.portfolio.service;

import com.portfolio.dto.DashboardResponse;
import com.portfolio.repository.CertificateRepository;
import com.portfolio.repository.ContactMessageRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;
    private final CertificateRepository certificateRepository;
    private final ContactMessageRepository messageRepository;

    public DashboardService(SkillRepository skillRepository,
                            ProjectRepository projectRepository,
                            CertificateRepository certificateRepository,
                            ContactMessageRepository messageRepository) {
        this.skillRepository = skillRepository;
        this.projectRepository = projectRepository;
        this.certificateRepository = certificateRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getDashboardStats() {
        long totalSkills = skillRepository.count();
        long totalProjects = projectRepository.count();
        long totalCertificates = certificateRepository.count();
        long unreadMessages = messageRepository.countByIsReadFalse();

        return DashboardResponse.builder()
                .totalSkills(totalSkills)
                .totalProjects(totalProjects)
                .totalCertificates(totalCertificates)
                .unreadMessages(unreadMessages)
                .build();
    }
}
