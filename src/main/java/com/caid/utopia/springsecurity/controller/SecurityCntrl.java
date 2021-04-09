package com.caid.utopia.springsecurity.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.caid.utopia.JWT.JwtRequest;
import com.caid.utopia.JWT.JwtResponse;
import com.caid.utopia.JWT.JwtTokenUtil;
import com.caid.utopia.JWT.JwtUserDetailsService;
import com.caid.utopia.entity.AccountRole;
import com.caid.utopia.entity.Account;
import com.caid.utopia.repo.AccountRepo;
import com.caid.utopia.springsecurity.service.SecurityService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SecurityCntrl {

//	@Autowired
//	AccountsRepo accountsRepo;
	
	@Autowired
	SecurityService securityService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
//	@Autowired
//	AccountsRepo accountsRepo;


	
	@ExceptionHandler({
//		RecordNotFoundException.class, //404
//		RecordCreationException.class, //404
//		RecordForeignKeyConstraintException.class, //409
//		RecordAlreadyExistsException.class, //409
//		RecordUpdateException.class, //400
	})
//	@Nullable
//	public final ResponseEntity<Object> handleException(Exception ex) throws Exception {
//		return ExceptionReducer.handleException(ex);
//	}
	

	@RequestMapping(path="/")
	public String baseEndPoint() {
		return "Base page";
	}
	
	@RequestMapping(path="/public")
	public String publicEndPoint() {
		return "Hello from Public";
	}
	
	@RequestMapping(path="/authenticated")
	public String authenticatedEndPoint() {
		return "Hello from authenticated";
	}
	
	//@RequestMapping(value="/user", method = RequestMethod.GET)
	@RequestMapping(path="/user")
	public String userEndPoint() {
		return "Hello from User";
	}
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	 @PreAuthorize("denyAll()")
//	 @Secured("ROLE_ADMIN")
	@RequestMapping(path="/admin")
	public String adminEndPoint() {
		return "Hello from Admin";
	}
	
	 
	@RequestMapping(value="/login", method = RequestMethod.GET, consumes="application/json")
	public String loginEndPoint() {
		return "Login Page";
	}
	@RequestMapping(value="/login", method = RequestMethod.POST, consumes="application/json")
	public String loginEndPoint2() {
		return "Login Page";
	}
	
	@RequestMapping(path="/login?error")
	public String loginErrorEndPoint() {
		return "Something went wrong.";
	}
	@RequestMapping(path="/logout.done")
	public String logoutdoneEndPoint() {
		return "Hopefully you are logged out";
	}
	
	@RequestMapping(path="/403")
	public String fourzerothreeEndPoint() {
		return "Sorry you do not have permission to view this page";
	}
	
	@RequestMapping(path="/logout")
	public String logoutEndPoint(HttpServletRequest request, HttpServletResponse response, Authentication auth) {        
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
		return "Logging you out....";
	}
	
	

//	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/Authentication", method = RequestMethod.GET, produces = "application/json")
	public Account getAuthentication() throws Exception {
		///add try and catch methods
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		System.out.println(SecurityContextHolder.getContext().getAuthentication());
//		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() =="anonymousUser")
//			return null;
//		Accounts a1 = new Accounts();
//		Accounts a1 =securityService.getAccountByName(username).get(0);
		Account a1 =securityService.getAccountByExactName(username);
		a1.setPassword(null);
		return a1;
	
		
	}
	
	///creates and returns the jwt token
	@RequestMapping(value = "/Authentication", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws AuthenticationException  {
		
		final Authentication authentication=	authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);
		
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
	
	

	
	
	@Transactional
	@RequestMapping(value = "/User", method = RequestMethod.POST, produces = "application/json")
	public Account registerUser(@RequestBody Account account) throws SQLException {
		///add try catch etc
		try {
		if(account.getPassword() == null) 
			throw  new Exception("Password is null");
		if(account.getUsername() == null) 
			throw  new Exception("Username is null");
		///Front end should handle same usernames so a request should never make this far with them
//		if(!(securityService.getAccountByName(account.getUsername()).isEmpty())) 
		if((securityService.getAccountByExactName(account.getUsername())!=null)) 
			throw  new Exception("Username is already taken");
		
//		if((account.getRoleId() == null)) 
//			throw  new Exception("RoleId is null");

		return securityService.registerUser(account); 
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			//return handleException(e);
			return null;
			
		}
		
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/Admin", method = RequestMethod.POST, produces = "application/json")
	public Account registerAdmin(@RequestBody Account account) throws SQLException {
		///add try catch etc
		try {
		if(account.getPassword() == null) 
			throw  new Exception("Password is null");
		if(account.getUsername() == null) 
			throw  new Exception("Username is null");
		///Front end should handle same usernames so a request should never make this far with them
//		if(!(securityService.getAccountByName(account.getUsername()).isEmpty()))
		if((securityService.getAccountByExactName(account.getUsername())!=null)) 
			throw  new Exception("Username is already taken");
//		if((account.getRoleId() == null)) 
//			throw  new Exception("RoleId is null");

		return securityService.registerAdmin(account); 
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			//return handleException(e);
			return null;
			
		}
		
	}
	
	
	

	
//	@Transactional
//	@RequestMapping(value = "/deleteAccount", method = RequestMethod.POST, produces = "application/json")
//	public ResponseEntity<?> deleteAccount(@RequestBody Accounts account) throws SQLException { 
//		try {
//			securityService.deleteAccount(account);
//			return new ResponseEntity<>(account, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("Failed to delete account", HttpStatus.BAD_REQUEST);
//		}
//	}

	
	///This is for testing only.
//	@RequestMapping(value = "/getAllAccounts", method = RequestMethod.GET, produces = "application/json")
//	public List<Account> getAllAccounts() {
//		List<Account> accounts = new ArrayList<>();
//		accounts = securityService.getAllAccounts();
//		return accounts;
//	}
//	

	

	
	
	
}
