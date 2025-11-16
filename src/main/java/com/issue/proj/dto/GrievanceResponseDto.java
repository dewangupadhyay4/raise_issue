package com.issue.proj.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GrievanceResponseDto {

	private Long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private String raisedBy;
    private String assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	
	
}
