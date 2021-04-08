package com.caid.utopia.springsecurity.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import com.caid.utopia.entity.Account;
import com.caid.utopia.entity.AccountRole;
import com.caid.utopia.repo.AccountRepo;

@Service
public class SecurityService {

	@Autowired
	AccountRepo accountsRepo;
	
	
	
	public Account registerUser(@RequestBody Account account) throws SQLException { 
		AccountRole ar = new AccountRole();
		ar.setRoleId(1);
		ar.setRoleType("ROLE_USER");
		account.setRoleId(ar);
		return addAccount(account);
	}
	
	public Account registerAdmin(@RequestBody Account account) throws SQLException { 
		AccountRole ar = new AccountRole();
		ar.setRoleId(2);
		ar.setRoleType("ROLE_ADMIN");
		account.setRoleId(ar);
		return addAccount(account);
	}
	

	public Account addAccount(@RequestBody Account account) throws SQLException { 
		String newPass = new BCryptPasswordEncoder().encode(account.getPassword());
		account.setPassword(newPass);
		return accountsRepo.save(account);
	}
	

	public ResponseEntity<?> deleteAccount(@RequestBody Account account) throws SQLException { 
		try {
			accountsRepo.delete(account);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Failed to delete account", HttpStatus.BAD_REQUEST);
		}
	}

	
//	public List<Accounts> deleteAccount(@RequestBody int id) throws SQLException { 
//		accountsRepo.findById
//		accountsRepo.delete(account);
//		return getAllAccounts();
//	}
	
	

	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<>();
		accounts = accountsRepo.findAll();
		return accounts;
	}
	

	public List<Account> getAccountByName(@RequestBody String username) throws SQLException { 
		///add code so only one (the right one) name gets returned
		List<Account> accounts = new ArrayList<>();
		accounts = accountsRepo.readAccountsByUserName(username);
		return accounts;
	}
	
	public Account getAccountByExactName(@RequestBody String username) throws Exception { 
		///add code so only one (the right one) name gets returned
		List<Account> accounts = new ArrayList<>();
		accounts = accountsRepo.getAccountByExactUserName(username);
		for(int i=0; i< accounts.size(); i++ ) {
			if(accounts.get(i).getUsername().equals(username))
				return accounts.get(i);
		}
		System.err.print("Something went wrong getting account");
		return null;
//		throw  new Exception("Username is already taken");
//		if(accounts.get(0).getUsername().equals(username))
//			return accounts.get(0);
//		else {
//			System.err.print("Something went wrong getting account");
//			throw  new Exception("Username is already taken");
//			//return null;
//		}

	}
	
}
