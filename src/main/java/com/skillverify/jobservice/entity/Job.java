package com.skillverify.jobservice.entity;
import java.util.List;
import java.util.UUID;

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
	private UUID publisherId;
	private String jobTitle;
	private String jobDescription;
	private int noOfOpenings;
	private boolean isExamRequired;
	private boolean isRound1Required;
	private boolean isRound2Required;
	 private String lastDateToApply; 
	 private String jobType; // e.g. Full-time, Part-time, Internship
	
	
	private int numberOfCandidatesShortlisted;
	private int numberCandidateApply;
	  private int experience;  
	private boolean isJobActive;
	
	@ElementCollection
	private List<String> location;
	
	@ElementCollection
	private List<String> requiredSkill;
	
	@ElementCollection
	private List<String> examTopics;

}