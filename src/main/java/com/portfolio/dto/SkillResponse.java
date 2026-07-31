package com.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponse {
    private Long id;
    private String skillName;
    private String category;
    private Integer percentage;
    private String icon;
    private Integer displayOrder;
    private Boolean enabled;
}
