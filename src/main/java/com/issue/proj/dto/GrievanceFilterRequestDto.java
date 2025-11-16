package com.issue.proj.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GrievanceFilterRequestDto {
	
	private String category;
	private String status;
	private String raisedBy;
	private String assignedTo;
	private LocalDateTime fromDate;
	private LocalDateTime toDate;

}
