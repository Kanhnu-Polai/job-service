package com.skillverify.jobservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillverify.jobservice.entity.Job;

public interface JobRepository extends JpaRepository<Job, UUID> {
	
	
	Job findByJobId(UUID jobId);
	//method to find job by jobTitle
	Job findByJobTitle(String jobTitle);
	//method to find job by jobType
	Job findByJobType(String jobType);
	
	List<Job> findByPublisherEmail(String email);
	List<Job> findAllByJobIdIn(List<UUID> jobIds);
	
	

}
