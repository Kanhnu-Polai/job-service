package com.skillverify.jobservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
	
	private String errorCode;
	private String errorMessage;

}
