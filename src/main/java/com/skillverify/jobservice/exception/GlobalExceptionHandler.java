package com.skillverify.jobservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skillverify.jobservice.contants.EmailNotVerifiedException;
import com.skillverify.jobservice.contants.ErrorCodeEnum;
import com.skillverify.jobservice.dto.ErrorResponseDto;

@ControllerAdvice

public class GlobalExceptionHandler {
	
	
	
	@ExceptionHandler(PublisherEmailOrIdMissingExeption.class)
	public ResponseEntity<ErrorResponseDto> handlePublisherEmailOrIdMissingException(PublisherEmailOrIdMissingExeption ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode(ErrorCodeEnum.PUBLISHER_EMAIL_OR_ID_MISSING.getCode())
				.errorMessage(ErrorCodeEnum.PUBLISHER_EMAIL_OR_ID_MISSING.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	
	
	@ExceptionHandler(InvalidJobIdException.class)
	public ResponseEntity<ErrorResponseDto> handleInvalidJobIdException(InvalidJobIdException ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode(ErrorCodeEnum.INVALID_JOB_ID.getCode())
				.errorMessage(ErrorCodeEnum.INVALID_JOB_ID.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	
	@ExceptionHandler(JobNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleJobNotFoundException(JobNotFoundException ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode(ErrorCodeEnum.JOB_NOT_FOUND.getCode())
				.errorMessage(ErrorCodeEnum.JOB_NOT_FOUND.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(EmailNotVerifiedException.class)
	public ResponseEntity<ErrorResponseDto> handleEmailNotVerifiedException(EmailNotVerifiedException ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode(ErrorCodeEnum.EMAIL_NOT_VERIFIED.getCode())
				.errorMessage(ErrorCodeEnum.EMAIL_NOT_VERIFIED.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(CompanyPhotoMissingException.class)
	public ResponseEntity<ErrorResponseDto> handleCompanyPhotoMissingException(CompanyPhotoMissingException ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode(ErrorCodeEnum.COMPANY_PHOTO_MISSING.getCode())
				.errorMessage(ErrorCodeEnum.COMPANY_PHOTO_MISSING.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(CompanyLogoFailedException.class)
	public ResponseEntity<ErrorResponseDto> handleCompanyLogoUploadFailedException(CompanyLogoFailedException ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode(ErrorCodeEnum.COMPANY_LOGO_UPLOAD_FAILED.getCode())
				.errorMessage(ErrorCodeEnum.COMPANY_LOGO_UPLOAD_FAILED.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	
	
	@ExceptionHandler(JobIdMissingException.class)
	public ResponseEntity<ErrorResponseDto> handleJobIdMissingException(JobIdMissingException ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode(ErrorCodeEnum.JOB_ID_MISSING.getCode())
				.errorMessage(ErrorCodeEnum.JOB_ID_MISSING.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(FailedToCallJobManagerServiceException.class)
	public ResponseEntity<ErrorResponseDto> handleFailedToCallJobManagerServiceException(FailedToCallJobManagerServiceException ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode(ErrorCodeEnum.FAILED_TO_CALL_JOB_MANAGER_SERVICE.getCode())
				.errorMessage(ErrorCodeEnum.FAILED_TO_CALL_JOB_MANAGER_SERVICE.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.errorCode("50000")
				.errorMessage("An unexpected error occurred: " + ex.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	
	
	

}
