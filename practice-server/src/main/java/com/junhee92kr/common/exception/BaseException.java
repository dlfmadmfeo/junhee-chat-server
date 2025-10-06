package com.junhee92kr.common.exception;

import com.junhee92kr.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
	private final ErrorCode errorCode;
	
	public BaseException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
