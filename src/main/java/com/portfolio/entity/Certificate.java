package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "certificate_name", nullable = false)
    private String certificateName;

    @Column(nullable = false)
    private String organization;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "credential_url")
    private String credentialUrl;

    @Column(name = "certificate_image")
    private String certificateImage;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
}
