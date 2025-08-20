package com.skillverify.jobservice.exception;

import com.skillverify.jobservice.contants.ErrorCodeEnum;

public class CompanyPhotoMissingException extends RuntimeException {
	
	private ErrorCodeEnum errorCodeEnum;
	public CompanyPhotoMissingException(ErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getMessage());
		this.errorCodeEnum = errorCodeEnum;
	}

}
