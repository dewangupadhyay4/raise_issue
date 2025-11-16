package com.issue.proj.dto;

import lombok.Data;

@Data
public class GrievanceRequestDto {
	
	private String title;
	private String description;
	private String category;
	private String raisedBy;

}
