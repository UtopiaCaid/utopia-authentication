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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.caid.utopia.UtopiaApplicationTests;

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
    	//assertNotEquals(200, status);
    	assertEquals(401, status);
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
    
    @Test
    public void accessAdminTest1() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/admin").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	//assertNotEquals(200, status);
    	assertEquals(401, status);
    }
    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void accessAdminTest2() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/admin").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	//assertNotEquals(200, status);
    	assertEquals(403, status);
    }
    @WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void accessAdminTest3() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/admin").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
   
    @Test
    public void getCurrentUserTest1() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/Authentication").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	//assertNotEquals(200, status);
    	assertEquals(401, status);
    }
    
    ///Fix so works regardless of data base
    @WithMockUser(username="user1",roles={"USER"},password="userpass")
    @Test
    public void getCurrentUserTest2() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/Authentication").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    @WithMockUser(username="admin1",roles={"ADMIN"}, password="adminpass")
    @Test
    public void getCurrentUserTest3() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/Authentication").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
    
//    @Test
//    public void getAlluserTest() throws Exception{
//    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/getAllAccounts").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//    	int status = result.getResponse().getStatus();
//    	assertEquals(200, status);
//    }
    
    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void logoutTest1() throws Exception{
    	assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    	 mvc.perform(MockMvcRequestBuilders.get("/logout").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
    
    @WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void logoutTest2() throws Exception{
    	assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    	 mvc.perform(MockMvcRequestBuilders.get("/logout").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
    
    

}
