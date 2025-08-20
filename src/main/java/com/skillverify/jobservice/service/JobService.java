package com.skillverify.jobservice.service;

import java.util.List;
import java.util.UUID;

import com.skillverify.jobservice.dto.JobCreationDto;
import com.skillverify.jobservice.dto.JobUpdationDto;
import com.skillverify.jobservice.entity.Job;

public interface JobService {
	public Job createJob(JobCreationDto jobCreateDto);
	public Job updateJob(JobUpdationDto jobUpdationDto, UUID jobId, String publisherEmail);
	public boolean deleteJob(UUID jobId, String publisherEmail);
	public List<Job> getAllJobsByEmail(String email);
	public Job updateShortListFieldById(UUID jobId);
	public Job updateNumberCandidateApply(UUID jobId);
	public Job updateJobStatus(String jobId, boolean isJobActive);
	public List<Job> getAllJobs();
	List<Job> getJobsByIds(List<UUID> jobIds);
	


}
