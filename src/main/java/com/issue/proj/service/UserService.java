package com.issue.proj.service;

import java.lang.foreign.Linker.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issue.proj.dto.LoginRequestDto;
import com.issue.proj.dto.LoginVerifyOtpRequestDto;
import com.issue.proj.dto.OtpVerifyRequestDto;
import com.issue.proj.dto.UserRegisterRequestDto;
import com.issue.proj.entity.User;
import com.issue.proj.entity.UserOtp;
import com.issue.proj.repository.UserOtpRepository;
import com.issue.proj.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private UserOtpRepository otpRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository repositoryUserRepository;
	
//	@Autowired
//	private LoginRequestDto loginRequestDto;
	
//	@Autowired
//	private LoginVerifyOtpRequestDto loginVerifyOtpRequestDto;

	
	public String sendOtp(UserRegisterRequestDto request) {
		String otp=String.format("%06d", new Random().nextInt(999999));
		UserOtp userOtp=UserOtp.builder()
				.email(request.getEmail())
				.otp(otp)
				.expiryTime(LocalDateTime.now().plusMinutes(5))
				.firstName(request.getFirstName())
				.middleName(request.getMiddleName())
				.lastName(request.getLastName())
				.password(request.getPassword())
				.phone(request.getPhone())
				.role(request.getRole())
				.stateCode(request.getStateCode())
				.build();
		otpRepository.save(userOtp);
		emailService.sendOtp(request.getEmail(), otp);
		return "OTP send to "+request.getEmail();
	}
	
	public String verifyOtp(OtpVerifyRequestDto verifyRequestDto) {
		Optional<UserOtp> otpOptional=otpRepository.findByEmail(verifyRequestDto.getEmail());
		if(otpOptional.isEmpty()) {
			throw new RuntimeException("No OTP found for this mail");
		}
		
		UserOtp otpRec=otpOptional.get();
		if(otpRec.getExpiryTime().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP has been expired ! please try again with new OTP");
		}
		
		if(!otpRec.getOtp().equals(verifyRequestDto.getOtp())) {
			throw new RuntimeException("Invalid OTP! Please enter correct one");
		}
		
		String username="User@"+UUID.randomUUID().toString().substring(0,6);
		
		User user= User.builder()
				.firstName(otpRec.getFirstName())
				.middleName(otpRec.getMiddleName())
				.lastName(otpRec.getLastName())
				.email(otpRec.getEmail())
				.phone(otpRec.getPhone())
				.password(otpRec.getPassword())
				.userName(username)
				.role(otpRec.getRole())
				.stateCode(otpRec.getStateCode())
				.build();
		repositoryUserRepository.save(user);
		otpRepository.delete(otpRec);
		return "Registration Successful";
		
	}
	
	public String loginUser(LoginRequestDto dto) {
		Optional<User> optionalUser=repositoryUserRepository.findByEmail(dto.getMail());
		
		if(optionalUser.isEmpty()) {
			throw new RuntimeException("No User Registerd with this mail");
		}
		
		User user=optionalUser.get();
		if(!user.getPassword().equals(dto.getPassword())) {
			throw new RuntimeException("Invalid exception");
		}
		
		String otp=String.format("%06d", new Random().nextInt(999999));
		UserOtp userOtp=UserOtp.builder()
				.email(user.getEmail())
				.otp(otp)
				.expiryTime(LocalDateTime.now().plusMinutes(5))
				.build();
		otpRepository.save(userOtp);
		emailService.sendOtp(user.getEmail(), otp);
		return "OTP Sent To Registered Mail";
		
	}
	
	public String verifyLoginOtp(LoginVerifyOtpRequestDto loginVerifyOtpRequestDto) {
		Optional<UserOtp> optionalUserOtp=otpRepository.findByEmail(loginVerifyOtpRequestDto.getEmail());
		if(optionalUserOtp.isEmpty()) {
			throw new RuntimeException("OTP cannot be blank");
		}
		
		UserOtp userOtp=optionalUserOtp.get();
		
		if(userOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP expired");
		}
		
		if(!userOtp.getOtp().equals(loginVerifyOtpRequestDto.getOtp())) {
			throw new RuntimeException("Invalid OTP");
		}
		
		otpRepository.delete(userOtp);
		
		return "Login Successfull";
	}
}
