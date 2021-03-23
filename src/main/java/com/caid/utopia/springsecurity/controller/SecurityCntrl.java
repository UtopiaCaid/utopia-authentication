package com.caid.utopia.springsecurity.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityCntrl {

	
	
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
      //  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  String contextPath = request.getContextPath();
	         System.out.println("Context path");
	         System.out.println(contextPath);
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
		return "Logging you out....";
	}
	
	@RequestMapping(value="/logout2", method = RequestMethod.GET)
	public String logoutDo(HttpServletRequest request,HttpServletResponse response, Authentication auth){
		
		 //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		   
         if (auth != null) {
       	  System.out.println("auth NOT null");
       	  System.out.println(auth.getName());
       	  System.out.println(auth.getAuthorities());
       	  
         }else {
       	  System.out.println("auth null");
         }
		
         String contextPath = request.getContextPath();
         System.out.println("Context path");
         System.out.println(contextPath);
         
		HttpSession session= request.getSession(false);
		SecurityContextHolder.clearContext();
		         session= request.getSession(false);
		        if(session != null) {
		            session.invalidate();
		        }
		        if(request.getCookies()!= null)
		        for(Cookie cookie : request.getCookies()) {
		            cookie.setMaxAge(0);
		        }
  
	          
//	          if (auth != null) {
//	              new SecurityContextLogoutHandler().logout(request, response, auth);
//	          }
	
		
		
//		SecurityContextHolder.getContext().setAuthentication(null);
		
		
		       
		      
		      //  response.reset();
		        
		        auth = SecurityContextHolder.getContext().getAuthentication();
		          if (auth != null) {
		        	  System.out.println("Didn'work");
		          }else {
		        	  System.out.println("still null");
		        	  System.out.println("");
		          }

		         // SecurityContextHolder.getContext().setAuthentication(null);

		          
		    return "logout";
	}
	
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }
	

	 @RequestMapping("/token")
	  public Map<String,String> token(HttpSession session) {
	    return Collections.singletonMap("token", session.getId());
	  }
	
	
}
