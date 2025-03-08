package com.aicrud.bookcrudsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aicrud.bookcrudsystem.entity.BorrowBook;

@Repository
public interface BorrowBookDAO extends JpaRepository<BorrowBook, Integer>{

	BorrowBook findByBook_bookIDAndUsers_userIDAndReturnDateIsNull(Integer bookID, Integer userID);

	BorrowBook findByBook_BookIDAndUsers_UserIDAndReturnDateIsNull(Integer bookID, Integer userID);

}
