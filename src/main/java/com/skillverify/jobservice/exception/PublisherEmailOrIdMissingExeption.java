package com.skillverify.jobservice.exception;

import com.skillverify.jobservice.contants.ErrorCodeEnum;

public class PublisherEmailOrIdMissingExeption extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	private ErrorCodeEnum errorCode;
	public PublisherEmailOrIdMissingExeption(ErrorCodeEnum errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		
		
	}

	

}
