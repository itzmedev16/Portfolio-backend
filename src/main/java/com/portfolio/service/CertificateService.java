package com.portfolio.service;

import com.portfolio.dto.CertificateRequest;
import com.portfolio.dto.CertificateResponse;
import com.portfolio.entity.Certificate;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    // Get all certificates sorted by display order
    @Transactional(readOnly = true)
    public List<CertificateResponse> getAllCertificates() {
        return certificateRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Create certificate
    @Transactional
    public CertificateResponse createCertificate(CertificateRequest request) {
        Certificate certificate = Certificate.builder()
                .certificateName(request.getCertificateName())
                .organization(request.getOrganization())
                .issueDate(request.getIssueDate())
                .credentialUrl(request.getCredentialUrl())
                .certificateImage(request.getCertificateImage())
                .displayOrder(request.getDisplayOrder())
                .build();
        Certificate savedCertificate = certificateRepository.save(certificate);
        return mapToResponse(savedCertificate);
    }

    // Update certificate
    @Transactional
    public CertificateResponse updateCertificate(Long id, CertificateRequest request) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + id));

        certificate.setCertificateName(request.getCertificateName());
        certificate.setOrganization(request.getOrganization());
        certificate.setIssueDate(request.getIssueDate());
        certificate.setCredentialUrl(request.getCredentialUrl());
        certificate.setCertificateImage(request.getCertificateImage());
        certificate.setDisplayOrder(request.getDisplayOrder());

        Certificate updatedCertificate = certificateRepository.save(certificate);
        return mapToResponse(updatedCertificate);
    }

    // Delete certificate
    @Transactional
    public void deleteCertificate(Long id) {
        if (!certificateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Certificate not found with ID: " + id);
        }
        certificateRepository.deleteById(id);
    }

    private CertificateResponse mapToResponse(Certificate certificate) {
        return CertificateResponse.builder()
                .id(certificate.getId())
                .certificateName(certificate.getCertificateName())
                .organization(certificate.getOrganization())
                .issueDate(certificate.getIssueDate())
                .credentialUrl(certificate.getCredentialUrl())
                .certificateImage(certificate.getCertificateImage())
                .displayOrder(certificate.getDisplayOrder())
                .build();
    }
}
