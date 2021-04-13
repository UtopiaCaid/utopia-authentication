package com.caid.utopia.springsecurity.controller;


import java.sql.SQLException;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.caid.utopia.JWT.JwtRequest;
import com.caid.utopia.JWT.JwtResponse;
import com.caid.utopia.JWT.JwtTokenUtil;

import com.caid.utopia.entity.Account;

import com.caid.utopia.springsecurity.service.SecurityService;

import exception.UsernameAlreadyExistsException;
import exception.WrongCredentialsException;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SecurityCntrl {

	
	@Autowired
	SecurityService securityService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	



	
	@ExceptionHandler({
//		BadCredentialsException.class,
		UsernameAlreadyExistsException.class,
//		UsernameNotFoundException.class,
		WrongCredentialsException.class,
	})

	

	@RequestMapping(path="/")
	public String baseEndPoint() {
			
			return "Base Page";
		
	}
	
	@RequestMapping(path="/public")
	public String publicEndPoint() {
		return "Hello from Public";
	}
	
	@RequestMapping(path="/authenticated")
	public String authenticatedEndPoint() {
		return "Hello from authenticated";
	}
	
	@RequestMapping(path="/userOnly")
	public String userEndPoint() {
		return "Hello from User";
	}
	

	@RequestMapping(path="/adminOnly")
	public String adminEndPoint() {
		return "Hello from Admin";
	}
	
	 

	@RequestMapping(value = "/authentication", method = RequestMethod.GET, produces = "application/json")
	public Account getAuthentication() throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account a1 =securityService.getAccountByExactName(username);
		a1.setPassword(null);
		return a1;
	
		
	}
	
	///Creates and returns the jwt token
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws AuthenticationException  {
		try {
		final Authentication authentication=	authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);
		
		return ResponseEntity.ok(new JwtResponse(token));
		}
		catch(BadCredentialsException e) {
//			System.err.println("BadCredentialsException");
			System.err.println(e.getMessage());
			//e.printStackTrace();
			throw new BadCredentialsException("BadCredentialsException msg");
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			throw new WrongCredentialsException();
		}
	}
	
	

	
	
	@Transactional
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json")
	public Account registerUser(@RequestBody Account account) throws SQLException {
		///add try catch etc
		try {
		if(account.getPassword() == null) 
			throw  new Exception("Password is null");
		if(account.getUsername() == null) 
			throw  new Exception("Username is null");
		///Front end should handle same usernames so a request should never make this far with them
		if((securityService.getAccountByExactName(account.getUsername())!=null)) 
			throw  new UsernameAlreadyExistsException();
		

		return securityService.registerUser(account); 
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			throw  new UsernameAlreadyExistsException();
			//return null;
			
		}
		
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/admin", method = RequestMethod.POST, produces = "application/json")
	public Account registerAdmin(@RequestBody Account account) throws SQLException {
		///add try catch etc
		try {
		if(account.getPassword() == null) 
			throw  new Exception("Password is null");
		if(account.getUsername() == null) 
			throw  new Exception("Username is null");
		///Front end should handle same usernames so a request should never make this far with them

		if((securityService.getAccountByExactName(account.getUsername())!=null)) 
			throw  new UsernameAlreadyExistsException();
//			throw  new Exception("Username is already taken");


		return securityService.registerAdmin(account); 
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			throw  new UsernameAlreadyExistsException();
//			return null;
			
		}
		
	}
	
	
	

	
	///This is for testing only.
//	@RequestMapping(value = "/getAllAccounts", method = RequestMethod.GET, produces = "application/json")
//	public List<Account> getAllAccounts() {
//		List<Account> accounts = new ArrayList<>();
//		accounts = securityService.getAllAccounts();
//		return accounts;
//	}
//	

	

	
	
	
}
