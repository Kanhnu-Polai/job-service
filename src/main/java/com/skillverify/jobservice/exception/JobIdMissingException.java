package com.skillverify.jobservice.exception;

import com.skillverify.jobservice.contants.ErrorCodeEnum;

public class JobIdMissingException extends RuntimeException {


	
	private ErrorCodeEnum errorCodeEnum;
	
	public JobIdMissingException(ErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getMessage());
		this.errorCodeEnum = errorCodeEnum;
	}
	
}
