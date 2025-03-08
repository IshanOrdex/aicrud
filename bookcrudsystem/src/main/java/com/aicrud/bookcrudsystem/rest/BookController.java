package com.aicrud.bookcrudsystem.rest;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aicrud.bookcrudsystem.dto.BookDTO;
import com.aicrud.bookcrudsystem.dto.ResponseDTO;
import com.aicrud.bookcrudsystem.exception.BookNotFoundException;
import com.aicrud.bookcrudsystem.exception.CustomException;
import com.aicrud.bookcrudsystem.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/book")
public class BookController {

	@Autowired
	private BookService bookService;

	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

	@PostMapping("/admin/")
	public ResponseDTO book(@Valid @RequestBody BookDTO bookDTO) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			LOGGER.info("In BookController -> add book API");

			bookService.addBook(bookDTO);

			responseDTO.setServiceResult("Book details saved successfully");
			responseDTO.setMessage("Book details saved successfully");
			responseDTO.setSuccess(1);

			LOGGER.info("Exiting BookController -> add book API");

		} catch (Exception ex) {

			responseDTO.setServiceResult("Error occurred while saving book details");
			responseDTO.setMessage("Error occurred while saving book details");
			responseDTO.setSuccess(0);
			ex.printStackTrace();
		}

		return responseDTO;
	}

	@PutMapping("/admin/update")
	public ResponseDTO updateBook(@Valid @RequestBody BookDTO bookDTO) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			LOGGER.info("In BookController -> Update book API");

			bookService.updateBook(bookDTO);

			responseDTO.setServiceResult("Book details updated successfully");
			responseDTO.setMessage("Book details updated successfully");
			responseDTO.setSuccess(1);

			LOGGER.info("Exiting BookController -> Update book API");

		} catch (Exception ex) {

			responseDTO.setServiceResult("Error occurred while updating book details");
			responseDTO.setMessage("Error occurred while updating book details");
			responseDTO.setSuccess(0);
		}

		return responseDTO;
	}

	@DeleteMapping("/admin/delete")
	public ResponseDTO deleteBook(@RequestParam("bookID") Integer bookID) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			LOGGER.info("In BookController -> delete book API");

			bookService.deleteBook(bookID);

			responseDTO.setServiceResult("Book details deleted successfully");
			responseDTO.setMessage("Book details deleted successfully");
			responseDTO.setSuccess(1);

			LOGGER.info("Exiting BookController -> delete book API");

		} catch (Exception ex) {

			responseDTO.setServiceResult("Error occurred while deleting book details");
			responseDTO.setMessage("Error occurred while deleting book details");
			responseDTO.setSuccess(0);
		}

		return responseDTO;
	}

	@GetMapping("/user/books")
	public ResponseDTO getBook() {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			LOGGER.info("In BookController -> get book API");

			List<BookDTO> bookDTOs = bookService.getBook();

			responseDTO.setServiceResult(bookDTOs);
			responseDTO.setMessage("Book details fetched successfully");
			responseDTO.setSuccess(1);

			LOGGER.info("Exiting BookController -> get book API");

		} catch (Exception ex) {

			responseDTO.setServiceResult("Error occurred while fetching book details");
			responseDTO.setMessage("Error occurred while fetching book details");
			responseDTO.setSuccess(0);
		}

		return responseDTO;
	}

	@PostMapping("/user/borrow")
	public ResponseDTO borrowBook(@RequestParam("bookID") Integer bookID, @RequestParam("userID") Integer userID) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			LOGGER.info("In BookController -> borrowBook API");

			bookService.borrowBook(bookID, userID);

			responseDTO.setServiceResult("Book borrowed successfully");
			responseDTO.setMessage("Book borrowed successfully");
			responseDTO.setSuccess(1);

			LOGGER.info("Exiting BookController -> borrowBook API");

		} catch (BookNotFoundException e) {

			responseDTO.setServiceResult(e.getMessage());
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(0);
		} catch (CustomException ce) {

			responseDTO.setServiceResult(ce.getMessage());
			responseDTO.setMessage(ce.getMessage());
			responseDTO.setSuccess(0);
		} catch (Exception ex) {

			responseDTO.setServiceResult("Error occurred while borrowing book");
			responseDTO.setMessage("Error occurred while borrowing book");
			responseDTO.setSuccess(0);
		}

		return responseDTO;
	}

	@PostMapping("/user/return")
	public ResponseDTO returnBook(@RequestParam("bookID") Integer bookID, @RequestParam("userID") Integer userID,
			@RequestParam("returnDate") LocalDate returnDate) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			LOGGER.info("In BookController -> returnBook API");

			bookService.returnBook(bookID, userID, returnDate);

			responseDTO.setServiceResult("Book returned successfully");
			responseDTO.setMessage("Book returned successfully");
			responseDTO.setSuccess(1);

			LOGGER.info("Exiting BookController -> returnBook API");

		} catch (Exception ex) {

			responseDTO.setServiceResult("Error occurred while returning book");
			responseDTO.setMessage("Error occurred while returning book");
			responseDTO.setSuccess(0);
		}

		return responseDTO;
	}

}
