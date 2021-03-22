package com.caid.utopia.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
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
}
