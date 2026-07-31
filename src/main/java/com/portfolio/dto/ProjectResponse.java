package com.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String shortDescription;
    private String detailedDescription;
    private String technologies;
    private String githubUrl;
    private String liveDemoUrl;
    private String image;
    private Boolean featured;
    private String status;
    private Integer displayOrder;
}
