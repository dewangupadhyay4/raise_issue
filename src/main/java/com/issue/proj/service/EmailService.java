package com.issue.proj.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;
	
	public void sendOtp(String toEmail, String otp) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Welcome to Issue portal ! Please verify your OTP");
		message.setText("Your One Time Password is : "+otp+"\n It is only valid for 5 minutes");
		javaMailSender.send(message);
	}
	
}
