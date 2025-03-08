package com.aicrud.bookcrudsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aicrud.bookcrudsystem.entity.Book;

@Repository
public interface BookDAO extends JpaRepository<Book, Integer>{

	Book findByBookID(Integer bookID);

}
