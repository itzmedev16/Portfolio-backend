package com.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceResponse {
    private Long id;
    private String title;
    private String organization;
    private String duration;
    private String description;
    private String type; // 'WORK' or 'EDUCATION'
    private Integer displayOrder;
    private Boolean enabled;
}
