package com.issue.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String password;
	private String userName;
	private String role;
	private String phone;
	private String stateCode;

}
