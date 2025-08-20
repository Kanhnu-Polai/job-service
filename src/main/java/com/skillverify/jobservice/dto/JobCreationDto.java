package com.skillverify.jobservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCreationDto {

   

    @Email
    private String publisherEmail;

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