package com.skillverify.jobservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skillverify.jobservice.contants.ErrorCodeEnum;
import com.skillverify.jobservice.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	
	
	
	
	
	
	
	
	
	
	
	@ExceptionHandler(CompanyLogoFailedException.class)
	public ResponseEntity<ErrorResponseDto> handleCompanyLogoUploadFailedException(CompanyLogoFailedException ex) {
		log.error("❌ Company logo upload failed: {}", ex.getMessage());
		return buildErrorResponse(ErrorCodeEnum.COMPANY_LOGO_UPLOAD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        errorResponse.put("errorCode", ErrorCodeEnum.VALIDATION_FAILED.getCode());
        errorResponse.put("errorMessage", ErrorCodeEnum.VALIDATION_FAILED.getMessage());
        errorResponse.put("fieldErrors", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	
	
	
	@ExceptionHandler(FailedToCallJobManagerServiceException.class)
	public ResponseEntity<ErrorResponseDto> handleFailedToCallJobManagerServiceException(FailedToCallJobManagerServiceException ex) {
	     log.error("❌ Failed to call Job Manager Service: {}", ex.getMessage());
		return buildErrorResponse(ErrorCodeEnum.FAILED_TO_CALL_JOB_MANAGER_SERVICE, HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
		log.error("❌ An unexpected error occurred: {}", ex.getMessage());
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode("50000")
				.errorMessage("An unexpected error occurred: " + ex.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	
	
	private ResponseEntity<ErrorResponseDto> buildErrorResponse(ErrorCodeEnum errorCode, HttpStatus status) {
	    ErrorResponseDto errorResponse = ErrorResponseDto.builder()
	            .errorCode(errorCode.getCode())
	            .errorMessage(errorCode.getMessage())
	            .build();
	    return ResponseEntity.status(status).body(errorResponse);
	}


}
