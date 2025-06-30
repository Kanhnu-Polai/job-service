package com.skillverify.jobservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCreateDto {

    /* ——— Who is creating this job? ——— */
    private UUID   publisherId;     // required
    private String publisherEmail;  // optional but handy for audits / e-mails

    /* ——— Core details ——— */
    private String jobTitle;
    private String jobDescription;
    private int    noOfOpenings;
    private int    experience;          // years
    private String jobType;            // e.g. Full-time, Part-time, Internship

    /* ——— Process flags ——— */
    private boolean examRequired;
    private boolean round1Required;
    private boolean round2Required;

    /* ——— Lists ——— */
    private List<String> requiredSkill;  // e.g. ["Java","Spring","Docker"]
    private List<String> location;       // e.g. ["Bengaluru","Remote"]
    private List<String> examTopics;     // e.g. ["DSA","Aptitude"]

    /* ——— Dates ——— */
    private LocalDate lastDateToApply;   // use ISO date on your front-end
}