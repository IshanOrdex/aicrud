package com.aicrud.bookcrudsystem.service;

import com.aicrud.bookcrudsystem.dto.UserLoginDTO;
import com.aicrud.bookcrudsystem.dto.UserRegistrationDTO;
import com.aicrud.bookcrudsystem.exception.CustomException;
import com.aicrud.bookcrudsystem.exception.EmailValidationException;

import jakarta.validation.Valid;

public interface UserService {

	void registerUser(@Valid UserRegistrationDTO userRegistrationDTO) throws EmailValidationException;

	String userLogin(@Valid UserLoginDTO userLoginDTO) throws CustomException;

}
