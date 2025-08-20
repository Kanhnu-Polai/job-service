package com.skillverify.jobservice.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobUpdationDto {
    @NotBlank
    private String jobTitle;

    @NotBlank
    private String jobDescription;
    
    @NotBlank
    private String companyName;

    @Min(1)
    private int noOfOpenings;

    @Min(0)
    private int experience;

    @NotBlank
    private String jobType;
    
    @NotBlank
    private String jobCategory;

    private boolean examRequired;
    private boolean round1Required;
    private boolean round2Required;
    private String companyPhotoLink;
    private String publicPhotoId;

    @NotEmpty
    private List<String> requiredSkill;

    @NotEmpty
    private List<String> location;

    private List<String> examTopics;

    @NotNull
    private LocalDate lastDateToApply;
}
