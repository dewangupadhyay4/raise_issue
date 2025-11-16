package com.issue.proj.mapper;

import org.mapstruct.Mapper;

import com.issue.proj.dto.UserRegisterRequestDto;
import com.issue.proj.entity.User;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

	
	@Mapping(target = "id", ignore = true)
	public User toEntity(UserRegisterRequestDto registerRequestDto);
	
	public UserRegisterRequestDto toDto(User user);
	
}
