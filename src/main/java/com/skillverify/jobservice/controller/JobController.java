package com.skillverify.jobservice.controller;


import com.skillverify.jobservice.contants.ErrorCodeEnum;
import com.skillverify.jobservice.dto.JobCreationDto;
import com.skillverify.jobservice.dto.JobUpdationDto;
import com.skillverify.jobservice.entity.Job;
import com.skillverify.jobservice.exception.*;
import com.skillverify.jobservice.service.CloudinaryService;
import com.skillverify.jobservice.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService        jobService;
    private final CloudinaryService cloudinaryService;

    @PostMapping(
        value      = "/create",
        consumes   = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces   = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Job> createJob(
        @Valid @RequestPart JobCreationDto jobCreateDto,
        @RequestPart("companyPhoto") MultipartFile companyPhoto ) {
        log.info("[CREATE] ⮕  {}", jobCreateDto.getJobTitle());

        // 1) Validate mandatory fields ---------------------------------------
        if (jobCreateDto.getPublisherEmail() == null || jobCreateDto.getPublisherEmail().isBlank()) {
            log.error("[CREATE] publisherEmail missing");
            throw new PublisherEmailOrIdMissingExeption(ErrorCodeEnum.PUBLISHER_EMAIL_OR_ID_MISSING);
        }

        if (companyPhoto == null || companyPhoto.isEmpty()) {
            log.error("[CREATE] companyPhoto missing");
            throw new CompanyPhotoMissingException(ErrorCodeEnum.COMPANY_PHOTO_MISSING);
        }

        // 2) Upload logo to Cloudinary ----------------------------------------
        Instant start = Instant.now();
        Map<String, String> upload;
        try {
            upload = cloudinaryService.uploadCompanyLogo(companyPhoto, "company_photos");
        } catch (Exception ex) {
            log.error("[CREATE] Cloudinary upload failed", ex);
            throw new CompanyLogoFailedException(ErrorCodeEnum.COMPANY_LOGO_UPLOAD_FAILED);
        }
        long ms = Duration.between(start, Instant.now()).toMillis();
        log.debug("[CREATE] Cloudinary upload ✔  {} ms,  URL={}", ms, upload.get("url"));

        // 3) Enrich DTO & persist --------------------------------------------
        jobCreateDto.setCompanyPhotoLink(upload.get("url"));
           jobCreateDto .setPublicPhotoId(upload.get("public_id"));

        Job saved = jobService.createJob(jobCreateDto);
        log.info("[CREATE] ✅  jobId={} publisher={}", saved.getJobId(), saved.getPublisherEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /* ───────────────────────────────────────────────────────────────
       DELETE /delete?jobId=&publisherEmail=
    ─────────────────────────────────────────────────────────────── */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteJob(
        @RequestParam UUID   jobId,
        @RequestParam String publisherEmail
    ) {
        log.info("[DELETE] ⮕  jobId={} publisher={}", jobId, publisherEmail);

        if (publisherEmail == null || publisherEmail.isBlank()) {
            log.error("[DELETE] publisherEmail missing");
            throw new PublisherEmailOrIdMissingExeption(ErrorCodeEnum.PUBLISHER_EMAIL_OR_ID_MISSING);
        }
        if (jobId == null) {
            log.error("[DELETE] jobId missing");
            throw new PublisherEmailOrIdMissingExeption(ErrorCodeEnum.INVALID_JOB_ID);
        }

        jobService.deleteJob(jobId, publisherEmail);
        log.info("[DELETE] ✅  jobId={} removed", jobId);

        return ResponseEntity.ok("Job deleted successfully");
    }

    /* ───────────────────────────────────────────────────────────────
       GET /getjobs/{email}
    ─────────────────────────────────────────────────────────────── */
    @GetMapping(value = "/getjobs/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Job>> getAllJobsByEmail(@PathVariable String email) {

        log.info("[GET] ⮕  all jobs for {}", email);

        if (email == null || email.isBlank()) {
            log.error("[GET] email path variable missing");
            throw new PublisherEmailOrIdMissingExeption(ErrorCodeEnum.PUBLISHER_EMAIL_OR_ID_MISSING);
        }

        List<Job> list = jobService.getAllJobsByEmail(email);
        log.debug("[GET] {} jobs found", list.size());

        return ResponseEntity.ok(list);
    }

    /* ----------------------------------------------------------------
       PUT endpoints below are placeholder — add logic as needed
    ---------------------------------------------------------------- */
	@PutMapping("/update/{jobId}")
	public ResponseEntity<Job> updateJob(@PathVariable UUID jobId, @RequestParam String publisherEmail,
			@Valid @RequestBody JobUpdationDto updationDto

	) {

		log.info("[UPDATE] ⮕  jobId={} publisher={}", jobId, publisherEmail);

		if (publisherEmail == null || publisherEmail.isBlank()) {
			log.error("[UPDATE] publisherEmail missing");
			throw new PublisherEmailOrIdMissingExeption(ErrorCodeEnum.PUBLISHER_EMAIL_OR_ID_MISSING);
		}

		if (jobId == null) {

			log.error("[UPDATE] jobId missing");
			throw new JobIdMissingException(ErrorCodeEnum.JOB_ID_MISSING);
		}
		
		// as i use @Valid annotation on JobUpdationDto, it will automatically validate the fields
		// so not need to check for null or empty fields here
		log.info("[UPDATE] jobId={} publisher={} with data: {}", jobId, publisherEmail, updationDto);
	 Job job =	jobService.updateJob(updationDto,jobId, publisherEmail);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(job);
    }

    @PutMapping("/updateShortListFieldById")
    public ResponseEntity<String> updateShortListFieldById() {
        log.warn("[SHORTLIST] Not implemented yet");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                             .body("Short-list update not implemented");
    }
    
    
    @GetMapping(value = "/getAllJobs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Job>> getAllJobs() {
		log.info("[GET] ⮕  all jobs");
		List<Job> list = jobService.getAllJobs();
		log.debug("[GET] {} jobs found", list.size());
		return ResponseEntity.ok(list);
	}
    
    @PutMapping("/updateNumberCandidateApply/{jobId}")
    public ResponseEntity<Job> updateNumberCandidateApply(	@PathVariable UUID jobId) {
		if (jobId == null) {
			log.error("[UPDATE] jobId missing");
			throw new JobIdMissingException(ErrorCodeEnum.JOB_ID_MISSING);
		}
		
		// Placeholder for actual logic to update number of candidates applied
		log.info("[UPDATE] jobId={} number of candidates applied", jobId);
		Job updatedJob = jobService.updateNumberCandidateApply(jobId); // Example increment by 1
		return ResponseEntity.status(HttpStatus.OK)
		                     .body(updatedJob);
						
	}
    
    
    
    @PostMapping(
    	    value = "/jobs/by-ids",
    	    consumes = MediaType.APPLICATION_JSON_VALUE,
    	    produces = MediaType.APPLICATION_JSON_VALUE
    	)
    	public ResponseEntity<List<Job>> getJobsByIds(@RequestBody List<UUID> jobIds) {
    	    log.info("[POST] ⮕  Fetching jobs for IDs: {}", jobIds);

    	    if (jobIds == null || jobIds.isEmpty()) {
    	        return ResponseEntity.badRequest().build();
    	    }

    	    List<Job> jobs = jobService.getJobsByIds(jobIds);
    	    return ResponseEntity.ok(jobs);
    	}
}