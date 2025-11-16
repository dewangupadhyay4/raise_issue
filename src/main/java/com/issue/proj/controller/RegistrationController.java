package com.issue.proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue.proj.dto.OtpVerifyRequestDto;
import com.issue.proj.dto.UserRegisterRequestDto;
import com.issue.proj.service.UserService;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/send-otp")
	public ResponseEntity<String> sendOtp(@RequestBody UserRegisterRequestDto dto){
		String message=userService.sendOtp(dto);
		return ResponseEntity.ok(message);
	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestBody OtpVerifyRequestDto dto){
		String verify=userService.verifyOtp(dto);
		return ResponseEntity.ok(verify);
	}

}
