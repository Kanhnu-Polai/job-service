package com.skillverify.jobservice.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JobDto {
	private String jobId;
    private String publisherEmail;
    private String jobTitle;
    private String companyName;
    private List<ApplicationDto> applications;

}
