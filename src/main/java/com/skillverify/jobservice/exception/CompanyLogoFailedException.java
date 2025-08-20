package com.skillverify.jobservice.exception;

import com.skillverify.jobservice.contants.ErrorCodeEnum;

public class CompanyLogoFailedException extends RuntimeException {
	private ErrorCodeEnum errorCode;
	public CompanyLogoFailedException(ErrorCodeEnum errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	

}
