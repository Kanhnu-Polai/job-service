package com.skillverify.jobservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResponseDto {

    /* ───────────────── Basic identifiers ───────────────── */
    private UUID   jobId;
    private String publisherEmail;

    /* ───────────────── Core job info ───────────────────── */
    private String companyName;
    private String jobTitle;
    private String jobDescription;
    private String jobType;           // e.g. Full-time / Contract
    private String jobCategory;       // e.g. Engineering / Marketing
    private int    experience;        // years
    private int    noOfOpenings;

    /* ───────────────── Flags & workflow ────────────────── */
    private boolean examRequired;
    private boolean round1Required;
    private boolean round2Required;
    private boolean jobActive;

    /* ───────────────── Skill + location lists ───────────── */
    private List<String> requiredSkill;
    private List<String> location;      // multiple cities / remote
    private List<String> examTopics;    // optional

    /* ───────────────── Candidate stats ──────────────────── */
    private int numberCandidateApply;
    private int numberOfCandidatesShortlisted;

    /* ───────────────── Media ────────────────────────────── */
    private String companyPhotoLink;    // public URL
    private String publicPhotoId;       // Cloudinary public_id (if needed)

    /* ───────────────── Dates ───────────────────────────── */
    private LocalDate      lastDateToApply;
    private LocalDateTime  createdAt;   // ★ new
    private LocalDateTime  updatedAt;   // (optional but handy)
}