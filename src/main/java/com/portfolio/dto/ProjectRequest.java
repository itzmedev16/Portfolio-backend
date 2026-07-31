package com.portfolio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Short description is required")
    private String shortDescription;

    private String detailedDescription;

    private String technologies;

    private String githubUrl;

    private String liveDemoUrl;

    private String image;

    @NotNull(message = "Featured flag is required")
    private Boolean featured;

    private String status;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be positive")
    private Integer displayOrder;
}
