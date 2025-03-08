package com.aicrud.bookcrudsystem.entity;

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
@Table(name="USERS")
public class Users {
	
	@Id
	@Column(name="UserID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userID;
	
	@Column(name="Name")
	private String name;
	
	@Column(name = "LibraryID", unique = true, nullable = false)
    private String libraryID;
	
	@Column(name = "Email", unique = true, nullable = false)
    private String email;
	
	@Column(name = "Password", nullable = false)
    private String password;
	
	@Column(name="ContactNo")
	private String contactNo;
	
	@ManyToOne
	@JoinColumn(name = "RoleID")
	private Role role;
	
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
