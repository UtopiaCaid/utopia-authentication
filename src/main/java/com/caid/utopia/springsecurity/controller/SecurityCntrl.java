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
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.caid.utopia.entity.AccountRoles;
import com.caid.utopia.entity.Accounts;
import com.caid.utopia.repo.AccountsRepo;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SecurityCntrl {

	@Autowired
	AccountsRepo accountsRepo;
	

	
	@RequestMapping(path="/public")
	public String publicEndPoint() {
		return "Hello from Public";
	}
	
	@RequestMapping(path="/authenticated")
	public String authenticatedEndPoint() {
		return "Hello from authenticated";
	}
	
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
	
	 
	@RequestMapping(path="/login")
	public String loginEndPoint() {
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
	
	
	@RequestMapping(path="/getSecurityContextHolder")
	public Authentication getSecurityContextHolder() {        
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
        
		return auth;
	}
	
	
	@RequestMapping(path="/getSecurityAccount")
	public Accounts getSecurityAccountr() {        
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
//		System.out.println("Auth getSecurityAccount stuff");
//		System.out.println(auth.getName());
//		System.out.println(auth.getAuthorities());
//		System.out.println(auth.getCredentials());
//		System.out.println(auth.getDetails());
//		System.out.println(auth.getPrincipal());
		Accounts account = new Accounts();
		AccountRoles user = new AccountRoles();
		user.setRoleId(0);
		user.setRoleType("ROLE_USER");
		AccountRoles admin = new AccountRoles();
		admin.setRoleId(1);
		admin.setRoleType("ROLE_ADMIN");
		account.setUsername(auth.getName());
		String roleName = auth.getAuthorities().iterator().next().toString();
		if((roleName.toString().equals("ROLE_ADMIN"))||auth.getAuthorities().contains("ROLE_ADMIN")) {
			//auth.getAuthorities().;
			account.setRoleId(admin);
		}
		else if(auth.getAuthorities().contains("USER")||auth.getAuthorities().contains("ROLE_USER")) {
			//auth.getAuthorities().;
			account.setRoleId(user);
		}
		else {
			///throw error here later
			System.out.println("role id not found");
		}
        
//		System.out.println("getSecurityAccount");
//		System.out.println(account.getUsername());
//		System.out.println(account.getRoleId());
//		System.out.println(admin.getRoleId());
		return account;
	}
	
	
	
	
//	@RequestMapping(value="/logout2", method = RequestMethod.GET)
//	public String logoutDo(HttpServletRequest request,HttpServletResponse response, Authentication auth){
//		
//		 //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		   
//         if (auth != null) {
//       	  System.out.println("auth NOT null");
//       	  System.out.println(auth.getName());
//       	  System.out.println(auth.getAuthorities());
//       	  
//         }else {
//       	  System.out.println("auth null");
//         }
//		
//         String contextPath = request.getContextPath();
//         System.out.println("Context path");
//         System.out.println(contextPath);
//         
//		HttpSession session= request.getSession(false);
//		SecurityContextHolder.clearContext();
//		         session= request.getSession(false);
//		        if(session != null) {
//		            session.invalidate();
//		        }
//		        if(request.getCookies()!= null)
//		        for(Cookie cookie : request.getCookies()) {
//		            cookie.setMaxAge(0);
//		        }
//  
//	          
////	          if (auth != null) {
////	              new SecurityContextLogoutHandler().logout(request, response, auth);
////	          }
//	
//		
//		
////		SecurityContextHolder.getContext().setAuthentication(null);
//		
//		
//		       
//		      
//		      //  response.reset();
//		        
//		        auth = SecurityContextHolder.getContext().getAuthentication();
//		          if (auth != null) {
//		        	  System.out.println("Didn'work");
//		          }else {
//		        	  System.out.println("still null");
//		        	  System.out.println("");
//		          }
//
//		         // SecurityContextHolder.getContext().setAuthentication(null);
//
//		          
//		    return "logout";
//	}
	
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }
	
	@Transactional
	@RequestMapping(value = "/registerAccount", method = RequestMethod.POST, produces = "application/json")
	public List<Accounts> registerAccount(@RequestBody Accounts account) throws SQLException { 
		
		//if statements makes sure no required values are null
		try {
			
		if(account.getPassword() == null) 
			throw  new Exception("Password is null");
		if(account.getAccountNumber() == null) 
			throw  new Exception("AccountNumber is null");
		if(account.getUsername() == null) 
			throw  new Exception("Username is null");
		if(account.getRoleId() == null) 
			throw  new Exception("RoleId is null");
		
		//If planing on sending an un-encoded password
		String newPass = new BCryptPasswordEncoder().encode(account.getPassword());
		account.setPassword(newPass);
			
		return addAccount(account);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return getAllAccounts();
			
		}
	}
	
	
	@Transactional
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST, produces = "application/json")
	public List<Accounts> addAccount(@RequestBody Accounts account) throws SQLException { 
		
		accountsRepo.save(account);
		return getAllAccounts();
	}
	
	@Transactional
	@RequestMapping(value = "/deleteAccount", method = RequestMethod.POST, produces = "application/json")
	public List<Accounts> deleteAccount(@RequestBody Accounts account) throws SQLException { 
		
		accountsRepo.delete(account);
		return getAllAccounts();
	}
	
	@RequestMapping(value = "/getAllAccounts", method = RequestMethod.GET, produces = "application/json")
	public List<Accounts> getAllAccounts() {
		List<Accounts> accounts = new ArrayList<>();
		accounts = accountsRepo.findAll();
		return accounts;
	}
	
	

	
	
	
}
