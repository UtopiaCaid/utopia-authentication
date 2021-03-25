package com.caid.utopia.springsecurity.controller;

import static org.junit.Assert.assertNotEquals;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import javax.servlet.ServletContext;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockServletContext;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//
//
//import com.caid.utopia.UtopiaApplication;
//import com.caid.utopia.UtopiaApplicationTests;
////import com.caid.utopia.repo.AccountsRepo;
//import com.caid.utopia.springsecurity.security.SecurityConfiguration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.caid.utopia.UtopiaApplicationTests;

//@WebAppConfiguration(value = "http://localhost:8080/")
//@WebMvcTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { UtopiaApplication.class, SecurityCntrl.class, SecurityConfiguration.class })
//@SpringBootTest(classes = UtopiaApplication.class)
public class SecurityCntrlTests extends UtopiaApplicationTests {
	
	
	//Logger logger = LoggerFactory.getLogger(SecurityCntrlTests.class);
	
	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
	}
	
	
    @Test
    public void accessPublicTest1() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/public").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void accessPublicTest2() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/public").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    @WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void accessPublicTest3() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/public").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
    @Test
    public void accessUserTest1() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertNotEquals(200, status);
    }
    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void accessUserTest2() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    @WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void accessUserTest3() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
    @WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void getCurrentUserTest1() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/getSecurityAccount").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
    
    @WithMockUser
    @Test
    public void test2() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void test3() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/admin").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
//    	System.out.println("Test3");
//    	System.out.println(status);
    	assertEquals(403, status);
    }
    
	@WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void test4() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/admin").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }

//	   @Autowired
//	    private MockMvc mvc;
//	   
//	   @Autowired
//	    private WebApplicationContext context;
//	   
//		@InjectMocks
//		private SecurityCntrl sc; 
//	   
//		@Mock 
//		AccountsRepo accountsRepo;
//	   
//	    @Before
//	    public void setup() {
////	    	   mvc = MockMvcBuilders
////	    		          .webAppContextSetup(context)
////	    		          .apply(springSecurity())
////	    		          .build();
//	     
////	        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//	    }
//	    
	    
//	    @Test
//	    public void test1() throws Exception{
////	    	RequestBuilder request = MockMvcRequestBuilders.get( "/public");
////	    	MvcResult result = mvc.perform(request).andReturn();
////	    	assertEquals("Hello from Public",result.getResponse().getContentAsString());
//	    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/public")).andReturn();
//	    	System.out.println("Test1");
//	    	System.out.println(result.getRequest().getContentAsString());
//	    }
	    
//	    @Test
//	    public void givenWac_whenServletContext_thenItProvidesGreetController() {
//	    	   mvc = MockMvcBuilders
//	    		          .webAppContextSetup(context)
//	    		          .apply(springSecurity())
//	    		          .build();
//	        ServletContext servletContext = context.getServletContext();
//	        
//	        Assert.assertNotNull(servletContext);
//	        Assert.assertTrue(servletContext instanceof MockServletContext);
//	        //Assert.assertNotNull(context.getBean("greetController"));
//	    }
	   
//	   @WithMockUser(username="user",roles={"USER"})
//	    @Test
//	    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
//	        mvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON))
//	          .andExpect(status().isOk());
//	    }
//	   
//	   @WithMockUser(username="user",roles={"USER"})
//	    @Test
//	    public void getPublic() throws Exception {
////		   this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
////		   mvc = MockMvcBuilders
////			          .webAppContextSetup(context)
////			          .apply(springSecurity())
////			          .build();
//	        mvc.perform(get("/public").contentType(MediaType.APPLICATION_JSON))
//	          .andExpect(status().isOk());
//	    }
//	   
//		@Test()
//		@WithMockUser
//		public void userPageAuthenticated() {
//		  // sc.userEndPoint();
//			System.out.println("user Auth (cntrl)");
//			System.out.println(sc.userEndPoint());
//		   assertEquals("Hello from User",sc.userEndPoint());
//		}
//		
//		@Test()
//		public void userPageUnauthenticated() {
//		  // sc.userEndPoint();
//			System.out.println("user Unuth (cntrl)");
//			System.out.println(sc.userEndPoint());
//		   assertNotEquals("Hello from User",sc.userEndPoint());
//		}
//	
}
