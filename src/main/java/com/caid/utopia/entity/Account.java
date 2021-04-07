/**
 * 
 */
package com.caid.utopia.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;


/**
 * @author dwoo
 *
 */
@Entity
@Table(name = "tbl_accounts")
public class Account implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6767878543794004350L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_number")
	private Integer accountNumber;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private AccountRole role;
	
	@Column(name = "username", length = 45)
	@NonNull
	private String username;
	
	@Column(name = "email", length = 45)
	@NonNull
	private String email;
	
	@Column(name = "password", length = 100)
	@NonNull
	private String password;
	
	@Column(name = "date_created")
	@NonNull
	private LocalDate dateCreated;

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public AccountRole getRoleId() {
		return role;
	}

	public void setRoleId(AccountRole role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}



}