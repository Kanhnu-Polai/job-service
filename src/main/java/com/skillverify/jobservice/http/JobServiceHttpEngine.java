package com.skillverify.jobservice.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.skillverify.jobservice.contants.ErrorCodeEnum;
import com.skillverify.jobservice.dto.JobDto;
import com.skillverify.jobservice.exception.FailedToCallJobManagerServiceException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobServiceHttpEngine {

    private final RestClient restClient;

    // externalize URL to application.properties
    @Value("${job-manager-service.url:http://127.0.0.1:5000/add_job}")
    private String jobManagerUrl;

    public JobServiceHttpEngine(RestClient restClient) {
        this.restClient = restClient;
    }

    public String makeCallToJobApplicationServiceToAddJob(JobDto jobDto) {
        try {
            log.info("📡 Making call to job application service to add job with ID: {}", jobDto.getJobId());

            ResponseEntity<String> response = restClient.post()
                    .uri(jobManagerUrl)
                    .body(jobDto)
                    .retrieve()
                    .toEntity(String.class);

            log.info("✅ Response from job-manager-service for jobId {}: {}", jobDto.getJobId(), response.getBody());

            return response.getBody();
        } catch (RestClientException ex) {
            log.error("❌ Error calling job-manager-service for jobId {}: {}", jobDto.getJobId(), ex.getMessage());
            throw new FailedToCallJobManagerServiceException(ErrorCodeEnum.FAILED_TO_CALL_JOB_MANAGER_SERVICE);
        }
    }
}
