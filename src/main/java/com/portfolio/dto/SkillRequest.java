package com.portfolio.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequest {

    @NotBlank(message = "Skill name is required")
    private String skillName;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Percentage is required")
    @Min(value = 0, message = "Percentage cannot be less than 0")
    @Max(value = 100, message = "Percentage cannot be more than 100")
    private Integer percentage;

    private String icon;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be positive")
    private Integer displayOrder;

    @NotNull(message = "Enabled flag is required")
    private Boolean enabled;
}
