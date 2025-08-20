package com.skillverify.jobservice.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationDto {
	private UUID applicationId;
    private String applicant_email;
    private String status;
    private String resume_link;

}
