package com.skillverify.jobservice.http;

import java.util.Collections;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.skillverify.jobservice.dto.JobDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobServiceHttpEngine {
	
	private final RestClient restClient;
	public JobServiceHttpEngine(RestClient restClient) {
		this.restClient = restClient;
	}
	
	
	public String makeCallToJobApplicationServiceToAddJob(JobDto jobDto) {
		log.info("📡 Making call to job application service to add job with ID: {}", jobDto.getJobId());
		ResponseEntity<String> response = restClient.post()
				.uri("http://127.0.0.1:5000/add_job")
				.body(jobDto)
				.retrieve()
				.toEntity(String.class);
		log.info("✅ Response from Flask service: {}", response.getBody());
		
		return "Job application service call made successfully";
	}

}
