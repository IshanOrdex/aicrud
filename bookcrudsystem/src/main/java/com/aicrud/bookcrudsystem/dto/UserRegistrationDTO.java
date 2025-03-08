package com.aicrud.bookcrudsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationDTO {
	
	private String name;
    private String email;
    private String password;
    private String contactNo;
    private Integer roleID;

}
