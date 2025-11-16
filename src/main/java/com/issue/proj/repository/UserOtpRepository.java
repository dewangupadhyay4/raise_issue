package com.issue.proj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.issue.proj.entity.UserOtp;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long>{
	
	Optional<UserOtp> findByEmail(String email);

}
