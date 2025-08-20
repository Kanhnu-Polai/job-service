package com.skillverify.jobservice.exception;

import com.skillverify.jobservice.contants.ErrorCodeEnum;

public class JobNotFoundException extends RuntimeException {
	private ErrorCodeEnum errorCode;
	public JobNotFoundException(ErrorCodeEnum errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}
