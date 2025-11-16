package com.issue.proj.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issue.proj.config.GrievanceSpecificationBuilder;
import com.issue.proj.dto.GrievanceFilterRequestDto;
import com.issue.proj.dto.GrievanceRequestDto;
import com.issue.proj.dto.GrievanceResponseDto;
import com.issue.proj.dto.GrievanceStatusUpdateDto;
import com.issue.proj.entity.Grievance;
import com.issue.proj.entity.User;
import com.issue.proj.mapper.GrievanceMapper;
import com.issue.proj.repository.GrievanceRepo;
import com.issue.proj.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GrievanceService {
	
	@Autowired
	private GrievanceRepo grievanceRepo;
	
	@Autowired
	private GrievanceMapper grievanceMapper;
	
	@Autowired
	private UserRepository userRepository;

	public GrievanceResponseDto createGrievance(GrievanceRequestDto requestDto) {
		
		Optional<User> user=userRepository.findByEmail(requestDto.getRaisedBy());
		if(user.isEmpty()) {
			throw new RuntimeException("User not registered");
		}
		
		Grievance grievance=Grievance.builder()
				.title(requestDto.getTitle())
				.description(requestDto.getDescription())
				.category(requestDto.getCategory())
				.status("NEW")
				.raisedBy(requestDto.getRaisedBy())
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		
		Grievance saved=grievanceRepo.save(grievance);
		return grievanceMapper.mapToResponse(saved);
	}
	
	public List<GrievanceResponseDto> getGrievanceByUser(String email){
		Optional<User> user=userRepository.findByEmail(email);
		if(user.isEmpty()) {
			throw new RuntimeException("User not registered");
		}
		return grievanceRepo.findByRaisedBy(email)
				.stream()
				.map(grievanceMapper::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<GrievanceResponseDto> getGrievancesAssignedTo(String officerEmail){
		Optional<User> user=userRepository.findByEmail(officerEmail);
		if(user.isEmpty()) {
			throw new RuntimeException("User not registered");
		}
		return grievanceRepo.findByAssignedTo(officerEmail)
				.stream()
				.map(grievanceMapper::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<GrievanceResponseDto> getAllGrievances(){
		return grievanceRepo.findAll()
				.stream()
				.map(grievanceMapper::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public GrievanceResponseDto updateGrievanceStatus(Long id, GrievanceStatusUpdateDto statusUpdateDto) {
		
		Optional<User> user=userRepository.findByEmail(statusUpdateDto.getAssignedTo());
		if(user.isEmpty()) {
			throw new RuntimeException("User not registered");
		}
		
		Grievance grievance=grievanceRepo.findById(id).orElseThrow(()-> new RuntimeException("Grievance Not found"));
		if(statusUpdateDto.getStatus() != null) grievance.setStatus(statusUpdateDto.getStatus()); 
		if(statusUpdateDto.getAssignedTo() != null) grievance.setAssignedTo(statusUpdateDto.getAssignedTo());
		grievance.setUpdatedAt(LocalDateTime.now());
		
		Grievance saved=grievanceRepo.save(grievance);
		return grievanceMapper.mapToResponse(saved);
	}
	
	public List<GrievanceResponseDto> filterGrievance(GrievanceFilterRequestDto filterRequestDto){
		
		var spec=GrievanceSpecificationBuilder.filter(filterRequestDto.getCategory(), filterRequestDto.getStatus(), filterRequestDto.getRaisedBy(), filterRequestDto.getAssignedTo(), filterRequestDto.getFromDate() != null ? filterRequestDto.getFromDate().with(LocalTime.MIN) : null, filterRequestDto.getToDate() != null ? filterRequestDto.getToDate().with(LocalTime.MAX) : null);
	
	return grievanceRepo.findAll(spec)
			.stream()
			.map(grievanceMapper::mapToResponse)
			.collect(Collectors.toList());
	}
	
	
}
