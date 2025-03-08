package com.aicrud.bookcrudsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aicrud.bookcrudsystem.entity.Users;

@Repository
public interface UserDAO extends JpaRepository<Users, Integer>{

	Users findByEmail(String email);

	Users findByUserID(Integer userID);

}
