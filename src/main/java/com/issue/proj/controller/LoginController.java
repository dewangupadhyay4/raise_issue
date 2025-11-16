package com.issue.proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue.proj.dto.LoginRequestDto;
import com.issue.proj.dto.LoginVerifyOtpRequestDto;
import com.issue.proj.service.UserService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class LoginController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
		String message=service.loginUser(dto);
		return ResponseEntity.ok(message);
	}
	
	@PostMapping("/verify-login")
	public ResponseEntity<String> verifyLogin(@RequestBody LoginVerifyOtpRequestDto verifyOtpRequestDto){
		String message=service.verifyLoginOtp(verifyOtpRequestDto);
		return ResponseEntity.ok(message);
	}

}
