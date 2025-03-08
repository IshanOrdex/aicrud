package com.aicrud.bookcrudsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.aicrud.bookcrudsystem.dto.BookDTO;
import com.aicrud.bookcrudsystem.exception.BookNotFoundException;
import com.aicrud.bookcrudsystem.exception.CustomException;

import jakarta.validation.Valid;

public interface BookService {

	void addBook(@Valid BookDTO bookDTO);

	void updateBook(@Valid BookDTO bookDTO);

	void deleteBook(Integer bookID);

	List<BookDTO> getBook();

	void borrowBook(Integer bookID, Integer userID) throws BookNotFoundException, CustomException;

	void returnBook(Integer bookID, Integer userID, LocalDate returnDate);

}
