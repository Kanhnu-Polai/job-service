package com.skillverify.jobservice.contants;

public class EmailNotVerifiedException extends RuntimeException {
	
	private ErrorCodeEnum errorCode;
	public EmailNotVerifiedException(ErrorCodeEnum errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}
