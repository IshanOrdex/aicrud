package com.aicrud.bookcrudsystem.serviceImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicrud.bookcrudsystem.dao.BookDAO;
import com.aicrud.bookcrudsystem.dao.BorrowBookDAO;
import com.aicrud.bookcrudsystem.dao.UserDAO;
import com.aicrud.bookcrudsystem.dto.BookDTO;
import com.aicrud.bookcrudsystem.entity.Book;
import com.aicrud.bookcrudsystem.entity.BorrowBook;
import com.aicrud.bookcrudsystem.entity.Users;
import com.aicrud.bookcrudsystem.exception.BookNotFoundException;
import com.aicrud.bookcrudsystem.exception.CustomException;
import com.aicrud.bookcrudsystem.service.BookService;

import jakarta.validation.Valid;

@Service
public class BookServiceImpl implements BookService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired
	private BookDAO bookDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BorrowBookDAO borrowBookDAO;

	@Override
	public void addBook(@Valid BookDTO bookDTO) {

		LOGGER.info("In BookserviceImpl -> addBook Method");

		Book book = new Book();

		BeanUtils.copyProperties(bookDTO, book);

		book.setCreatedAt(new Date());
		book.setCreatedBy("Admin");

		bookDAO.save(book);

		LOGGER.info("Exiting BookserviceImpl -> addBook Method");
	}

	@Override
	public void updateBook(@Valid BookDTO bookDTO) {

		LOGGER.info("In BookserviceImpl -> updateBook Method");

		if (bookDTO.getBookID() != null) {

			Book bookDetails = bookDAO.findByBookID(bookDTO.getBookID());

			BeanUtils.copyProperties(bookDTO, bookDetails);

			bookDetails.setModifiedBy("Admin");
			bookDetails.setModifiedAt(new Date());

			bookDAO.save(bookDetails);
		}

		LOGGER.info("Exiting BookserviceImpl -> updateBook Method");
	}

	@Override
	public void deleteBook(@Valid Integer bookID) {

		LOGGER.info("In BookserviceImpl -> deleteBook Method");

		bookDAO.deleteById(bookID);

		LOGGER.info("Exiting BookserviceImpl -> deleteBook Method");
	}

	@Override
	public List<BookDTO> getBook() {
		LOGGER.info("In BookserviceImpl -> getBookDetails Method");

		List<BookDTO> bookDetails = bookDAO.findAll().stream()
				.map(book -> new BookDTO(book.getBookID(), book.getName(), book.getAuthor(), book.getAvailableCopies()))
				.collect(Collectors.toList());

		LOGGER.info("Exiting BookserviceImpl -> getBookDetails Method");
		return bookDetails;
	}

	@Override
	public void borrowBook(Integer bookID, Integer userID) throws BookNotFoundException, CustomException {

		LOGGER.info("In BookserviceImpl -> borrowBook Method");

		Book book = bookDAO.findByBookID(bookID);

		Users user = userDAO.findByUserID(userID);

		if (book != null) {

			if (book.getAvailableCopies() <= 0) {

				throw new CustomException("Book is not avilable");
			}

			book.setAvailableCopies(book.getAvailableCopies() - 1);

			bookDAO.save(book);

			BorrowBook borrowBook = new BorrowBook();

			borrowBook.setBook(book);
			borrowBook.setUsers(user);
			borrowBook.setBorrowDate(new Date());
			borrowBook.setDueDate(LocalDate.now().plusDays(14));
			borrowBook.setCreatedAt(new Date());
			borrowBook.setCreatedBy("Admin");

			borrowBookDAO.save(borrowBook);

		} else {
			throw new BookNotFoundException("Book not found");
		}

		LOGGER.info("Exiting BookserviceImpl -> borrowBook Method");
	}

	@Override
	public void returnBook(Integer bookID, Integer userID, LocalDate returnDate) {
		
		LOGGER.info("In BookserviceImpl -> returnBook Method");
		
		BorrowBook borrowBook = borrowBookDAO.findByBook_BookIDAndUsers_UserIDAndReturnDateIsNull(bookID,userID);
		
		if(borrowBook != null) {
			
			borrowBook.setReturnDate(returnDate);
			borrowBook.setLateFee(calculateLateFee(borrowBook.getDueDate(), returnDate));
			
			borrowBookDAO.save(borrowBook);
			
			Book book = bookDAO.findByBookID(bookID);
			
			book.setAvailableCopies(book.getAvailableCopies() + 1);
			
			bookDAO.save(book);
		}
		
		LOGGER.info("Exiting BookserviceImpl -> returnBook Method");
	}
	
	private double calculateLateFee(LocalDate dueDate, LocalDate returnDate) {
	    long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
	    return Math.max(daysLate, 0) * 5.0;
	}

}
