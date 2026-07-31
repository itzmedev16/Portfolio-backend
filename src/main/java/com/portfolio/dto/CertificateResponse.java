package com.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateResponse {
    private Long id;
    private String certificateName;
    private String organization;
    private LocalDate issueDate;
    private String credentialUrl;
    private String certificateImage;
    private Integer displayOrder;
}
