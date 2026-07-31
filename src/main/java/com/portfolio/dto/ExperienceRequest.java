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
public class ExperienceRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Organization is required")
    private String organization;

    @NotBlank(message = "Duration is required")
    private String duration;

    private String description;

    @NotBlank(message = "Type is required")
    private String type; // 'WORK' or 'EDUCATION'

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be positive")
    private Integer displayOrder;

    @NotNull(message = "Enabled flag is required")
    private Boolean enabled;
}
