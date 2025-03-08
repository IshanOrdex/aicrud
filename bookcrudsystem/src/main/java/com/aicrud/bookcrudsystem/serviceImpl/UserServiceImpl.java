package com.aicrud.bookcrudsystem.serviceImpl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aicrud.bookcrudsystem.dao.RoleDAO;
import com.aicrud.bookcrudsystem.dao.UserDAO;
import com.aicrud.bookcrudsystem.dto.UserLoginDTO;
import com.aicrud.bookcrudsystem.dto.UserRegistrationDTO;
import com.aicrud.bookcrudsystem.entity.Role;
import com.aicrud.bookcrudsystem.entity.Users;
import com.aicrud.bookcrudsystem.exception.CustomException;
import com.aicrud.bookcrudsystem.exception.EmailValidationException;
import com.aicrud.bookcrudsystem.service.UserService;
import com.aicrud.bookcrudsystem.util.JwtUtil;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public void registerUser(@Valid UserRegistrationDTO userRegistrationDTO) throws EmailValidationException {

		LOGGER.info("In UserServiceImpl -> registerUser Method");

		Users users = userDAO.findByEmail(userRegistrationDTO.getEmail());

		if (users != null) {

			throw new EmailValidationException("Email already used! Please try with different one");
		}

		String encryptedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());

		String libraryID = "LIB" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		Users user = new Users();

		BeanUtils.copyProperties(userRegistrationDTO, user);

		user.setPassword(encryptedPassword);

		user.setLibraryID(libraryID);

		Optional<Role> role = null;

		if (null != userRegistrationDTO.getRoleID())
			role = roleDAO.findById(userRegistrationDTO.getRoleID());

		if (null != role.get())
			user.setRole(role.get());

		user.setCreatedAt(new Date());
		user.setCreatedBy("Admin");

		userDAO.save(user);

		LOGGER.info("Exiting UserServiceImpl -> registerUser Method");
	}

	@Override
	public String userLogin(@Valid UserLoginDTO userLoginDTO) throws CustomException {

		LOGGER.info("In UserServiceImpl -> loginUser Method");

		Users user = userDAO.findByEmail(userLoginDTO.getEmail());

		if (user == null || !passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {

			throw new CustomException("Invalid email or password");
		}

		LOGGER.info("Exiting UserServiceImpl -> loginUser Method");

		return jwtUtil.generateToken(user.getEmail());

	}

}
