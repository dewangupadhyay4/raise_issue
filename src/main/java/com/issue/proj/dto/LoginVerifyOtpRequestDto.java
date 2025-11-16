package com.issue.proj.dto;

import lombok.Data;

@Data
public class LoginVerifyOtpRequestDto {
	
	private String email;
	private String otp;

}
