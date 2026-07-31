package com.portfolio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequest {

    @NotBlank(message = "Certificate name is required")
    private String certificateName;

    @NotBlank(message = "Organization is required")
    private String organization;

    private LocalDate issueDate;

    private String credentialUrl;

    private String certificateImage;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be positive")
    private Integer displayOrder;
}
