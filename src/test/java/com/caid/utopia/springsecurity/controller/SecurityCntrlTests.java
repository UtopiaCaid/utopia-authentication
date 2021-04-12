package com.caid.utopia.springsecurity.controller;

import static org.junit.Assert.assertFalse;




import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


import com.fasterxml.jackson.annotation.JsonInclude;

import com.caid.utopia.UtopiaApplicationTests;
import com.caid.utopia.entity.Account;
import com.caid.utopia.entity.AccountRole;
import com.caid.utopia.repo.AccountRepo;
import com.caid.utopia.repo.AccountRoleRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



public class SecurityCntrlTests extends UtopiaApplicationTests {
	
	
	
	@Autowired
	AccountRepo accountsRepo;
	
	@Autowired
	AccountRoleRepo accountRolesRepo;
	
	
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
    public void accessBaseTest1() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void accessBaseTest2() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    @WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void accessBaseTest3() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
    @Test
    public void accessAuthTest1() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/authenticated").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(401, status);
    }
    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void accessAuthTest2() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/authenticated").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
    @WithMockUser(username="admin",roles={"ADMIN"})
    @Test
    public void accessAuthTest3() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/authenticated").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    
    @Test
    public void accessUserTest1() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
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
    	assertEquals(401, status);
    }
    @WithMockUser(username="user",roles={"USER"})
    @Test
    public void accessAdminTest2() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/admin").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
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
    	assertEquals(401, status);
    }
    
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
    
    @WithMockUser(username="h2user",roles={"USER"},password="userpass")
    @Test
    public void getCurrentUserTest4() throws Exception{
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/Authentication").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	assertEquals(200, status);
    }
    

    
    
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
    

  
    @Test
    public void canReadAllUsers()  throws Exception{
    	List<Account> accounts = new ArrayList<>();
		accounts = accountsRepo.findAll();
		assertNotNull(accounts);
		
    }
    
    @Test
    public void canReadAllAccountRoles()  throws Exception{
    	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
		assertNotNull(accountRoles);
		
    }
    
    
    @Transactional
    @Test
    public void canCreateUser() throws Exception{
    	boolean accountFound= false;
    	accountFound= doesAccountExistByName("testUser");
		assertFalse(accountFound);
		createUserAccount("testUser");
		accountFound= doesAccountExistByName("testUser");
		assertTrue(accountFound);
    	
    }
    
    
    @Transactional
    @Test
    public void createUserAccountError1() throws Exception{
     	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(0);
    	Account newUser = new Account();
    	newUser.setUsername("username");
    	newUser.setRoleId(userRole);
    	newUser.setEmail("username@email.com");
    	
    	
    	 ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    	 System.setErr(new PrintStream(errContent));
    	
    	MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/User")
  			  .content(asJsonString(newUser))
  			  .contentType(MediaType.APPLICATION_JSON)
  			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    	assertEquals("",result.getResponse().getContentAsString());
    
    }
    
    
    @Transactional
    @Test
    public void createUserAccountError2() throws Exception{
     	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(0);
    	Account newUser = new Account();
    	newUser.setPassword("testpass");
    	newUser.setRoleId(userRole);
    	newUser.setEmail("username@email.com");  	
    	MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/User")
  			  .content(asJsonString(newUser))
  			  .contentType(MediaType.APPLICATION_JSON)
  			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	
    	assertEquals("",result.getResponse().getContentAsString());
    
    }
    
    @Transactional
    @Test
    public void createUserAccountError3() throws Exception{
     	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(0);
    	Account newUser = new Account();
    	newUser.setUsername("user1");
    	newUser.setPassword("testpass");
    	newUser.setRoleId(userRole);
    	newUser.setEmail("username@email.com");  	
    	MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/User")
  			  .content(asJsonString(newUser))
  			  .contentType(MediaType.APPLICATION_JSON)
  			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	
    	assertEquals("",result.getResponse().getContentAsString());
    
    }
    
    
    
    @Transactional
    @Test
    public void createAdminAccountError1() throws Exception{
     	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(1);
    	Account newUser = new Account();
    	newUser.setUsername("username");
    	newUser.setRoleId(userRole);
    	newUser.setEmail("username@email.com");  	
    	MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/Admin")
  			  .content(asJsonString(newUser))
  			  .contentType(MediaType.APPLICATION_JSON)
  			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	
    	assertEquals("",result.getResponse().getContentAsString());
    
    }
    
    @Transactional
    @Test
    public void createAdminAccountError2() throws Exception{
     	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(1);
    	Account newUser = new Account();
    	newUser.setRoleId(userRole);
    	newUser.setPassword("testpass");
    	newUser.setEmail("username@email.com");  	
    	MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/Admin")
  			  .content(asJsonString(newUser))
  			  .contentType(MediaType.APPLICATION_JSON)
  			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	
    	assertEquals("",result.getResponse().getContentAsString());
    
    }
    
    @Transactional
    @Test
    public void createAdminAccountError3() throws Exception{
     	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(1);
    	Account newUser = new Account();
    	newUser.setUsername("admin1");
    	newUser.setRoleId(userRole);
    	newUser.setPassword("testpass");
    	newUser.setEmail("username@email.com");  	
    	MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/Admin")
  			  .content(asJsonString(newUser))
  			  .contentType(MediaType.APPLICATION_JSON)
  			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	
    	assertEquals("",result.getResponse().getContentAsString());
    
    }
    
  
    
    @Transactional
    @Test
    public void canCreateAdmin() throws Exception{
    	boolean accountFound= false;
    	accountFound= doesAccountExistByName("testAdmin");
		assertFalse(accountFound);
		createAdminAccount("testAdmin");
		accountFound= doesAccountExistByName("testAdmin");
		assertTrue(accountFound);
    	
    }
    
    

    
    @Transactional
    @Test
    public void canNotAuthenticateWrongUser() throws Exception{
    	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(0);
    	Account newUser = new Account();
    	newUser.setUsername("wronguser");
    	newUser.setRoleId(userRole);
    	newUser.setPassword("wrongpass");
    	newUser.setEmail("wronguser@email.com");
    	 MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/Authentication")
     			  .content(asJsonString(newUser))
     			  .contentType(MediaType.APPLICATION_JSON)
     			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	int status = result.getResponse().getStatus();
    	String errorMsg = result.getResponse().getErrorMessage();
    	assertEquals(401, status);
    	assertEquals("Unauthorized", errorMsg);
    }
    
    @Transactional
    @Test
    public void canNotAuthenticateWithWrongUserPass() throws Exception{
    	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(0);
    	Account newUser = new Account();
    	newUser.setUsername("user1");
    	newUser.setRoleId(userRole);
    	newUser.setPassword("wrongpass");
    	newUser.setEmail("wronguser@email.com");

    	 MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/Authentication")
     			  .content(asJsonString(newUser))
     			  .contentType(MediaType.APPLICATION_JSON)
     			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    	int status = result.getResponse().getStatus();
    	String errorMsg = result.getResponse().getErrorMessage();
    	assertEquals(401, status);
    	assertEquals("Unauthorized", errorMsg);
    }
    
    
    
    
    @Transactional
    @Test
    public void canAuthenticateNewUser() throws Exception{
   
    	///Can't have two of the same entities with the same id
		Account testUserNoId = createUserAccount("testUser");
	
		///So will store the id here
		Integer tuID = getAccountByName("testUser").getAccountNumber();
		
		Account ac = authenticateAccount(testUserNoId);
		assertTrue(areAccountsSame(ac, ac, ac.getAccountNumber()));
		assertTrue(areAccountsSame(testUserNoId, ac, tuID));	
    }
    
    @Transactional
    @Test
    public void canAuthenticateNewAdmin() throws Exception{
   
    	///Can't have two of the same entities with the same id
		Account testAdminNoId = createUserAccount("testAdmin");
	
		///So will store the id here
		Integer taID = getAccountByName("testAdmin").getAccountNumber();
		
		///Account returned by Authenticate
		Account ac = authenticateAccount(testAdminNoId);
		assertTrue(areAccountsSame(ac, ac, ac.getAccountNumber()));
		assertTrue(areAccountsSame(testAdminNoId, ac, taID));	
    }
   
    
    
    
    
/////******************************** Helpers *********************************************************************************
    @Test
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  
    
    @Test
    public static <T>  Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        return mapper.readValue(json, objectClass);
    }
    
    @Test
    public  boolean doesAccountExistByName(String username) {
    	List<Account> accounts = new ArrayList<>();
		accounts = accountsRepo.findAll();
		for (Account a1: accounts) {
			if(a1.getUsername().equals(username)) 
				return true;	
		}
		return false;
    }
    
    @Test
    public  Account getAccountByName(String username) throws Exception {
    	List<Account> accounts = new ArrayList<>();
		accounts = accountsRepo.findAll();
		for (Account a1: accounts) {
			if(a1.getUsername().equals(username)) 
				return a1;	
		}
		throw  new Exception("Can't Find user");
    }
    

    
    
  
    
    @Test
    public  Account createAdminAccount(String username) throws Exception{
     	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole adminRole = accountRoles.get(1);
    	Account newAdmin = new Account();
    	newAdmin.setUsername(username);
    	newAdmin.setRoleId(adminRole);
    	newAdmin.setPassword("testpass");
    	newAdmin.setEmail(username+"@email.com");
    	mvc.perform(MockMvcRequestBuilders.post("/Admin")
    			  .content(asJsonString(newAdmin))
    			  .contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON_VALUE));
    	return newAdmin;
    
    }
    

    
    @Test
    public Account createUserAccount(String username) throws Exception{
     	List<AccountRole> accountRoles = new ArrayList<>();
    	accountRoles = accountRolesRepo.findAll();
    	AccountRole userRole = accountRoles.get(0);
    	Account newUser = new Account();
    	newUser.setUsername(username);
    	newUser.setRoleId(userRole);
    	newUser.setPassword("testpass");
    	newUser.setEmail(username+"@email.com");
    	mvc.perform(MockMvcRequestBuilders.post("/User")
  			  .content(asJsonString(newUser))
  			  .contentType(MediaType.APPLICATION_JSON)
  			.accept(MediaType.APPLICATION_JSON_VALUE));
    	return newUser;

    
    }
    
 
    
    @Transactional
    @Test
    public Account authenticateAccount(Account account) throws Exception{
    	
  	  MvcResult response = mvc.perform(MockMvcRequestBuilders.post("/Authentication")
  			  .content(asJsonString(account))
  			  .contentType(MediaType.APPLICATION_JSON)
  			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
  
  	  //Get token string from Authentication request
  	  String resString = response.getResponse().getContentAsString();
  	  String token =  resString.substring(10, resString.indexOf("}")-1);
  		 
  	//Use token to check if the user is now authenticated (returns account of authenticated user if true)
	  MvcResult response2 = mvc.perform(MockMvcRequestBuilders.get("/Authentication")
  			.header("Authorization", "Bearer " + token)
  			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
  
	  String resString2 = response2.getResponse().getContentAsString();
	  Object account2= convertJSONStringToObject(resString2, Account.class);
	  Account a2 = (Account) account2;	  
	  return a2;
    }
    

    
    @Test
    public boolean areAccountsSame(Account ac1, Account ac2, int id) {
    	if(id!= ac2.getAccountNumber())
    		return false;
    	
    	if(!ac1.getUsername().equals(ac2.getUsername()))
    		return false;
    	
    	if(!ac1.getRoleId().getRoleType().equals(ac2.getRoleId().getRoleType()))
    		return false;
    	
    	if(ac1.getRoleId().getRoleId() != ac2.getRoleId().getRoleId())
    		return false;
    	
    	return true;
    	
    }
    

    
	
////DeBugg tool
//  @Test
//    public  void printAllUsers() throws Exception{
//    	List<Account> accounts = new ArrayList<>();
//		accounts = accountsRepo.findAll();
//		for (Account a1: accounts) 
//			System.out.println(a1.getUsername());
//    }

}
