package com.junhee92kr.chat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@ToString
public class UserLoginRequestDto {
	private String password;
	private String email;
}
