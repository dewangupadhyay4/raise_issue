package com.issue.proj.mapper;

import org.mapstruct.Mapper;

import com.issue.proj.dto.GrievanceResponseDto;
import com.issue.proj.entity.Grievance;

@Mapper(componentModel = "spring")
public interface GrievanceMapper {
	
	
	public GrievanceResponseDto mapToResponse(Grievance grievance);

}
