package com.issue.proj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue.proj.dto.GrievanceFilterRequestDto;
import com.issue.proj.dto.GrievanceRequestDto;
import com.issue.proj.dto.GrievanceResponseDto;
import com.issue.proj.dto.GrievanceStatusUpdateDto;
import com.issue.proj.service.GrievanceService;


@RestController
@RequestMapping("/api/v1/grievance")
@CrossOrigin("*")
public class GrievanceController {

	@Autowired
	private GrievanceService grievanceService;
	
	@PostMapping("/create")
	public ResponseEntity<GrievanceResponseDto> createGrievance(@RequestBody GrievanceRequestDto grievanceRequestDto){
		return ResponseEntity.ok(grievanceService.createGrievance(grievanceRequestDto));
	}
	
	@GetMapping("/user/{email}")
	public ResponseEntity<List<GrievanceResponseDto>> getUserGrievance(@PathVariable String email){
		return ResponseEntity.ok(grievanceService.getGrievanceByUser(email));
	}
	
	@GetMapping("/officer/{email}")
	public ResponseEntity<List<GrievanceResponseDto>> getOfficerGrievance(@PathVariable String email){
		return ResponseEntity.ok(grievanceService.getGrievancesAssignedTo(email));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<GrievanceResponseDto>> getAllGrievance(){
		return ResponseEntity.ok(grievanceService.getAllGrievances());
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<GrievanceResponseDto> updateGrievance(@PathVariable Long id, @RequestBody GrievanceStatusUpdateDto updateDto){
		return ResponseEntity.ok(grievanceService.updateGrievanceStatus(id, updateDto));
	}
	
	@PostMapping("/filter")
	public List<GrievanceResponseDto> filterGrievances(@RequestBody GrievanceFilterRequestDto dto) {
	    return grievanceService.filterGrievance(dto);
	}
	
	
}
