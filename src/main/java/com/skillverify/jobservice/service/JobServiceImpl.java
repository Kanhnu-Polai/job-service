package com.skillverify.jobservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.skillverify.jobservice.contants.EmailNotVerifiedException;
import com.skillverify.jobservice.contants.ErrorCodeEnum;
import com.skillverify.jobservice.dto.JobCreationDto;
import com.skillverify.jobservice.dto.JobDto;
import com.skillverify.jobservice.dto.JobUpdationDto;
import com.skillverify.jobservice.entity.Job;
import com.skillverify.jobservice.exception.FailedToCallJobManagerServiceException;
import com.skillverify.jobservice.exception.JobNotFoundException;
import com.skillverify.jobservice.exception.PublisherEmailOrIdMissingExeption;
import com.skillverify.jobservice.http.JobServiceHttpEngine;
import com.skillverify.jobservice.repository.JobRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class JobServiceImpl implements JobService {
	
	
	private final JobRepository jobRepository;
	private final JobServiceHttpEngine jobServiceHttpEngine;
	
	private final CloudinaryService cloudinaryService;

	@Override
	public Job createJob(JobCreationDto jobCreateDto) {
		log.info("Creating job with details: {}", jobCreateDto.getJobTitle());
		log.info("Publisher email: {}", jobCreateDto.getPublisherEmail());
		Job job = Job.builder()
			
				.publisherEmail(jobCreateDto.getPublisherEmail())
				.jobTitle(jobCreateDto.getJobTitle())
				.jobDescription(jobCreateDto.getJobDescription())
				.noOfOpenings(jobCreateDto.getNoOfOpenings())
				.isExamRequired(jobCreateDto.isExamRequired())
				.companyName(jobCreateDto.getCompanyName())
				.companyPhotoLink(jobCreateDto.getCompanyPhotoLink())
				.publicPhotoId(jobCreateDto.getPublicPhotoId())
				.isRound1Required(jobCreateDto.isRound1Required())
				.isRound2Required(jobCreateDto.isRound2Required())
				.lastDateToApply(jobCreateDto.getLastDateToApply())
				.jobCategory(jobCreateDto.getJobCategory())
				.jobType(jobCreateDto.getJobType())
				.numberOfCandidatesShortlisted(0) // Initial value
				.numberCandidateApply(0) // Initial value
				.experience(jobCreateDto.getExperience())
				.location(jobCreateDto.getLocation())
				.requiredSkill(jobCreateDto.getRequiredSkill())
				.examTopics(jobCreateDto.getExamTopics())
				.createdAt(LocalDateTime.now()) // Set the creation date to today
				.build();
		
		
		
		
		jobRepository.save(job);
		log.info("Job created successfully with ID: {}", job.getJobId());
		
		JobDto jobDto = JobDto.builder()
				.jobId(job.getJobId().toString())
				.publisherEmail(job.getPublisherEmail())
				.jobTitle(job.getJobTitle())
				.companyName(job.getCompanyName())
				.applications(List.of()) 
				.build();
		
		// Call to external service to add job
		
		try {
			jobServiceHttpEngine.makeCallToJobApplicationServiceToAddJob(jobDto);
		} catch (Exception e) {
			log.error("❌ Error calling job application service: {}", e.getMessage());
			throw new FailedToCallJobManagerServiceException(ErrorCodeEnum.FAILED_TO_CALL_JOB_MANAGER_SERVICE);
		}
		
		return job;
	}

	

	@Override
	public boolean deleteJob(UUID jobId, String publisherEmail) {
	    log.info("JobServiceImpl -> deleteJob() called with jobId: {} and publisherEmail: {}", jobId, publisherEmail);

	    

	   

	  
	    Optional<Job> jobOptional = jobRepository.findById(jobId);

	    if (jobOptional.isEmpty()) {
	        log.error("No job found with ID: {}", jobId);
	        throw new JobNotFoundException(ErrorCodeEnum.JOB_NOT_FOUND);
	    }

	    Job job = jobOptional.get();
	    String publicId = job.getPublicPhotoId();

	    if (!publisherEmail.equalsIgnoreCase(job.getPublisherEmail())) {
	        log.error("Unauthorized delete attempt for jobId: {} by email: {}", jobId, publisherEmail);
	        throw new EmailNotVerifiedException(ErrorCodeEnum.EMAIL_NOT_VERIFIED);
	    }

	    try {
	        jobRepository.deleteById(jobId);
	        cloudinaryService.deleteFile(publicId);
	        log.info("Job with ID: {} deleted successfully", jobId);
	        return true;
	    } catch (Exception e) {
	        log.error("Error deleting job with ID: {}. Error: {}", jobId, e.getMessage());
	        throw new RuntimeException("Something went wrong while deleting the job");
	    }
	}

	@Override
	public List<Job> getAllJobsByEmail(String email) {
		log.info("JobServiceImpl -> getAllJobsByEmail() method called with email: {}", email);
		
		List<Job> jobs = jobRepository.findByPublisherEmail(email);
		log.info("" + jobs.size() + " jobs found for email: " + email);
		if (jobs.isEmpty()) {
			
			log.warn("No jobs found for email: {}", email);
			return List.of(); // Return an empty list if no jobs found
		}
		
		log.info("Returning {} jobs for email: {}", jobs.size(), email);
		return jobs;
	}

	@Override
	public Job updateShortListFieldById(UUID jobId) {
		log.info("Updating shortlist field for job ID: {}", jobId);
		Optional<Job> jobOptional = jobRepository.findById(jobId);
		if (jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setNumberOfCandidatesShortlisted(job.getNumberOfCandidatesShortlisted() + 1);
			jobRepository.save(job);
			log.info("Shortlist field updated successfully for job ID: {}", jobId);
			return job;
		} else {
			log.error("Job with ID: {} not found", jobId);
			throw new JobNotFoundException(ErrorCodeEnum.JOB_NOT_FOUND);
		}
		
	}

	@Override
	public Job updateNumberCandidateApply(UUID jobId) {
		log.info("Updating number of candidates applied for job ID: {}", jobId);
		Optional<Job> jobOptional = jobRepository.findById(jobId);
		if (jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setNumberCandidateApply(job.getNumberCandidateApply() + 1);
			jobRepository.save(job);
			log.info("Number of candidates applied updated successfully for job ID: {}", jobId);
			return job;
		} else {
			log.error("Job with ID: {} not found", jobId);
			throw new JobNotFoundException(ErrorCodeEnum.JOB_NOT_FOUND);}
	}

	@Override
	public Job updateJobStatus(String jobId, boolean isJobActive) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Job updateJob(JobUpdationDto jobUpdationDto, UUID jobId, String publisherEmail) {
	
		log.info("Updating job with ID: {} for publisher: {}", jobId, publisherEmail);
		Job existingJob = jobRepository.findById(jobId).orElseThrow(() -> {
			log.error("Job with ID: {} not found", jobId);
			return new JobNotFoundException(ErrorCodeEnum.JOB_NOT_FOUND);
		});
		
		if (!existingJob.getPublisherEmail().equalsIgnoreCase(publisherEmail)) {
			log.error("Unauthorized update attempt for jobId: {} by email: {}", jobId, publisherEmail);
			throw new EmailNotVerifiedException(ErrorCodeEnum.EMAIL_NOT_VERIFIED);
		}
		
		// Update fields only if they are not null
		if (jobUpdationDto.getJobTitle() != null) {
			existingJob.setJobTitle(jobUpdationDto.getJobTitle());
		}
		if (jobUpdationDto.getJobDescription() != null) {
			existingJob.setJobDescription(jobUpdationDto.getJobDescription());
		}
		if (jobUpdationDto.getNoOfOpenings() != 0) {
			existingJob.setNoOfOpenings(jobUpdationDto.getNoOfOpenings());
		}
		if (jobUpdationDto.getCompanyName() != null) {
			existingJob.setCompanyName(jobUpdationDto.getCompanyName());
		}
		if (jobUpdationDto.getCompanyPhotoLink() != null) {
			existingJob.setCompanyPhotoLink(jobUpdationDto.getCompanyPhotoLink());
		}
		if (jobUpdationDto.getPublicPhotoId() != null) {
			existingJob.setPublicPhotoId(jobUpdationDto.getPublicPhotoId());
		}
		if (jobUpdationDto.getLastDateToApply() != null) {
			existingJob.setLastDateToApply(jobUpdationDto.getLastDateToApply());
		}
		if (jobUpdationDto.getJobCategory() != null) {
			existingJob.setJobCategory(jobUpdationDto.getJobCategory());
		}
		if (jobUpdationDto.getJobType() != null) {
			existingJob.setJobType(jobUpdationDto.getJobType());
		}
		if (jobUpdationDto.getExperience() >=0) {
			existingJob.setExperience(jobUpdationDto.getExperience());
		}
		if (jobUpdationDto.getLocation() != null) {
			existingJob.setLocation(jobUpdationDto.getLocation());
		}
		if (jobUpdationDto.getRequiredSkill() != null) {
			existingJob.setRequiredSkill(jobUpdationDto.getRequiredSkill());
		}
		if (jobUpdationDto.getExamTopics() != null) {
			existingJob.setExamTopics(jobUpdationDto.getExamTopics());
		}
		existingJob.setExamRequired(jobUpdationDto.isExamRequired());
		existingJob.setRound1Required(jobUpdationDto.isRound1Required());
		existingJob.setRound2Required(jobUpdationDto.isRound2Required());
		
		jobRepository.save(existingJob);
		log.info("Job with ID: {} updated successfully", jobId);
		return existingJob;
		
		
		
	
	}



	@Override
	public List<Job> getAllJobs() {
		log.info("Fetching all jobs from the repository");
		List<Job> allJobs = jobRepository.findAll();
		if (allJobs.isEmpty()) {
			log.warn("No jobs found in the repository");
			return List.of(); // Return an empty list if no jobs found
		}
		log.info("Returning {} jobs from the repository", allJobs.size());
		return allJobs;
		
	}


	@Override
	public List<Job> getJobsByIds(List<UUID> jobIds) {
	    return jobRepository.findAllByJobIdIn(jobIds);
	}

}
