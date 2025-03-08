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
public class BookDTO {
	
	private Integer bookID;
	
	private String name;
	
	private String author;
	
	private Integer availableCopies;

}
