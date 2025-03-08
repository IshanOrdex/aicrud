package com.aicrud.bookcrudsystem.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicrud.bookcrudsystem.dto.ResponseDTO;
import com.aicrud.bookcrudsystem.dto.UserLoginDTO;
import com.aicrud.bookcrudsystem.dto.UserRegistrationDTO;
import com.aicrud.bookcrudsystem.exception.CustomException;
import com.aicrud.bookcrudsystem.exception.EmailValidationException;
import com.aicrud.bookcrudsystem.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/user")
public class UserController {

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/register")
	public ResponseDTO user(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			LOGGER.info("In UserController -> RegisterUser API");

			userService.registerUser(userRegistrationDTO);

			responseDTO.setServiceResult("User details registred successfully");
			responseDTO.setMessage("User details registred successfully");
			responseDTO.setSuccess(1);

			LOGGER.info("Exiting UserController -> RegisterUser API");

		}
		catch (EmailValidationException e) {

			responseDTO.setServiceResult(e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(0);
		}
		
		catch (Exception ex) {

			responseDTO.setServiceResult("Error occurred while register user details");
			responseDTO.setMessage("Error occurred while register user details");
			responseDTO.setSuccess(0);
			ex.printStackTrace();
		}

		return responseDTO;

	}

	@PostMapping("/login")
	public ResponseDTO userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			LOGGER.info("In UserController -> LogInUser API");

			String token = userService.userLogin(userLoginDTO);

			responseDTO.setServiceResult(token);
			responseDTO.setMessage("User Logged-In successfully");
			responseDTO.setSuccess(1);

			LOGGER.info("Exiting UserController -> LogInUser API");

		}

		catch (CustomException e) {

			responseDTO.setServiceResult(e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(0);
		} catch (Exception ex) {

			responseDTO.setServiceResult("Error occurred while login");
			responseDTO.setMessage("Error occurred while login");
			responseDTO.setSuccess(0);
			ex.printStackTrace();
		}

		return responseDTO;

	}
}
