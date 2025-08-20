package com.skillverify.jobservice.contants;


public enum ErrorCodeEnum {
	
	VALIDATION_FAILED("JOB_SERVICE_7000", "Validation failed"),
	
	JOB_NOT_FOUND("JOB_SERVICE_70001","Job not found"),
	
	EMAIL_NOT_VERIFIED("JOB_SERVICE_70002","Email not verified"),
	
	COMPANY_LOGO_UPLOAD_FAILED("JOB_SERVICE_70003","Company logo upload failed"), 
	
	FAILED_TO_CALL_JOB_MANAGER_SERVICE("JOB_SERVICE_70004","Failed to call Job Manager Service"),
	FILE_READ_ERROR("JOB_SERVICE_70005","File read error during Cloudinary upload"),
	CLOUDINARY_UPLOAD_FAILED("JOB_SERVICE_70006","Failed to upload file to Cloudinary"),;
	

	private final String message;
	private final String code;

	ErrorCodeEnum(String code, String message) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}
	public String getCode() {
		return code;
	}

}
