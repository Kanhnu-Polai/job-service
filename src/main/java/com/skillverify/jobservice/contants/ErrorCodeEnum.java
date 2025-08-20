package com.skillverify.jobservice.contants;


public enum ErrorCodeEnum {
	
	
	PUBLISHER_EMAIL_OR_ID_MISSING("70000","Publisher email or ID is missing"),
	JOB_NOT_FOUND("70001","Job not found"),
	INVALID_JOB_ID("70002","Job Id not provided"),
	INVALID_JOB_STATUS("70003","Invalid job status provided"),
	INVALID_EXAM_TOPICS("70004","Invalid exam topics provided"),
	INVALID_LOCATION("70005","Invalid location provided"),
	INVALID_SKILL("70006","Invalid skill provided"),
	EXAM_NOT_REQUIRED_FOR_JOB("70007","Exam is not required for this job"),
	ROUND1_NOT_REQUIRED_FOR_JOB("70008","Round 1 is not required for this job"),
	ROUND2_NOT_REQUIRED_FOR_JOB("70009","Round 2 is not required for this job"),
	EMAIL_NOT_VERIFIED("70010","Email not verified"),
	COMPANY_PHOTO_MISSING("70011","Company photo is missing"),
	COMPANY_LOGO_UPLOAD_FAILED("70012","Company logo upload failed"), 
	JOB_ID_MISSING("70013","Job ID is missing"),
	FAILED_TO_CALL_JOB_MANAGER_SERVICE("70014","Failed to call Job Manager Service"),;
	

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
