package com.aicrud.bookcrudsystem.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

@Entity
@Table(name="BOOK")
public class Book {
	
	@Id
	@Column(name="BookID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookID;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Author")
	private String author;
	
	@Column(name="AvailableCopies")
	private Integer availableCopies;
	
	@Column(name = "Active")
	private Byte active;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CreatedAt")
	private Date createdAt;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "ModifiedAt")
	private Date modifiedAt;

}
