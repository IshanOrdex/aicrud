package com.aicrud.bookcrudsystem.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="BORROWBOOK")
public class BorrowBook {

	@Id
	@Column(name="BorrowID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer borrowID;
	
	@ManyToOne
	@JoinColumn(name = "BookID")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "UserID")
	private Users users;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "BorrowDate")
	private Date borrowDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "DueDate")
	private LocalDate dueDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "ReturnDate")
	private LocalDate returnDate;

	@Column(name = "LateFee")
	private Double lateFee;

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
