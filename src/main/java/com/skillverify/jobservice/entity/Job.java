package com.skillverify.jobservice.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
	
	@Id
	@GeneratedValue
	private UUID jobId;
	private String publisherEmail; 
	private String companyName;
	private String jobTitle;
	@Column(columnDefinition = "TEXT")
	private String jobDescription;
	private int noOfOpenings;
	private boolean isExamRequired;
	private boolean isRound1Required;
	private boolean isRound2Required;
	 private LocalDate lastDateToApply; 
	 private String jobType; 
	 private String jobCategory;
	 private String companyPhotoLink;
	 private String publicPhotoId;
	
	
	private int numberOfCandidatesShortlisted;
	private int numberCandidateApply;
	  private int experience;  

	
	@ElementCollection
	private List<String> location;
	
	@ElementCollection
	private List<String> requiredSkill;
	
	@ElementCollection
	private List<String> examTopics;
	@CreatedDate
    private LocalDateTime createdAt;

    // (Optional) Track last updated time
  
}