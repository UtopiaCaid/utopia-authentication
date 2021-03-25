package com.caid.utopia;
//

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = UtopiaApplication.class)
@ContextConfiguration(classes = { UtopiaApplication.class})
@WebAppConfiguration
public abstract class UtopiaApplicationTests {

	protected MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	WebApplicationContext webApplicationContext;

	
	
	protected void setUp() {
		//mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	    mvc = MockMvcBuilders
	            .webAppContextSetup(webApplicationContext)
	            .apply(springSecurity())
	            .build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.readValue(json, clazz);
	}

//	@Test
//	void contextLoads() {
//
//	}

//	@Test
//	void testAssertion() {
//		assertTrue(true);
//	}

	
}
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
////import static org.junit.Assert.assertNotNull;
////import static org.junit.Assert.assertNull;
////import static org.junit.Assert.assertTrue;
////import static org.mockito.Mockito.atLeast;
////import static org.mockito.Mockito.atMost;
////import static org.mockito.Mockito.never;
////import static org.mockito.Mockito.timeout;
////import static org.mockito.Mockito.times;
////import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
////import static org.mockito.Mockito.anyInt;
//import static org.junit.Assert.assertThrows;
//
//import com.caid.utopia.springsecurity.controller.SecurityCntrl;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.web.context.WebApplicationContext;
//
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = UtopiaApplication.class)
//@WebAppConfiguration
//class UtopiaApplicationTests {
//	
//	@InjectMocks
//	private SecurityCntrl sc; 
//
//	//private PermissionEvaluatorProxy permissionEvaluator;
//	
////	@Test
////	void contextLoads() {
////	}
////	
//	
//	@BeforeEach()
//	  public void setup() {
//		
//	}
//	
////	@Test()
////	public void publicPageUnauthenticated() {
////	   //sc.userEndPoint();
////		System.out.println("Public");
////		System.out.println(sc.publicEndPoint());
////	   assertEquals("Hello from Public",sc.publicEndPoint());
////	   
////	}
////	
////	@Test()
////	public void userPageUnauthenticated() {
////	   //sc.userEndPoint();
////		System.out.println("user unauth (main)");
////		System.out.println(sc.userEndPoint());
////	   assertNotEquals("Hello from User",sc.userEndPoint());
////	   
////	}
//////	
////	@Test()
////	@WithMockUser
////	public void userPageAuthenticated() {
////	  // sc.userEndPoint();
////		System.out.println("user Auth (main)");
////		System.out.println(sc.userEndPoint());
////	   assertEquals("Hello from User",sc.userEndPoint());
////	}
////	
////	@Test()
////	@WithMockUser(username="user",roles={"USER"})
////	public void AdminPageUnauthenticated() {
////	  // sc.adminEndPoint();
////		
////		System.out.println("admin Unauth");
////		System.out.println(sc.adminEndPoint());
////		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
////		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
////	   assertNotEquals("Hello from Admin",sc.adminEndPoint());
////	}
////	
////	@Test()
////	@WithMockUser(username="admin",roles={"USER","ADMIN"})
////	public void AdminPageAuthenticated() {
////		System.out.println("admin Auth");
////		System.out.println(sc.adminEndPoint());
////	   assertEquals("Hello from Admin",sc.adminEndPoint());
////	}
////	
////	@Test
////	public void shouldThrowAccessDeniedException() {
////	  assertThrows(AccessDeniedException.class, () -> sc.adminEndPoint());
////	}
//
//}
