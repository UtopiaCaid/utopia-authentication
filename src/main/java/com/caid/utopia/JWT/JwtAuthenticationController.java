/////////
//////Keeping for now in case something breaks down the line rn its not needed
//package com.caid.utopia.JWT;
//
////import java.sql.SQLException;
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Objects;
//
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
////import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
////import com.caid.utopia.entity.Accounts;
////import com.caid.utopia.repo.AccountsRepo;
//
//
//
//
//@RestController
//@CrossOrigin
//public class JwtAuthenticationController {
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//	
////	@Autowired
////	AccountsRepo accountsRepo;
//
////	@Autowired
////	private JwtUserDetailsService userDetailsService;
//
//	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws AuthenticationException  {
//	
//		
//		
//		final Authentication authentication=	authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
//		
//		
////		final UserDetails userDetails = userDetailsService
////				.loadUserByUsername(authenticationRequest.getUsername());
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		final String token = jwtTokenUtil.generateToken(authentication);
//		
//		return ResponseEntity.ok(new JwtResponse(token));
//		
//	}
//
////	private void authenticate(String username, String password) throws Exception {
////		try {
////			System.out.println("Authenticated1_________________");
////			System.out.println(SecurityContextHolder.getContext().getAuthentication());
////			System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
////			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
////			System.out.println("Authenticated2_________________");
////			System.out.println(SecurityContextHolder.getContext().getAuthentication());
////			System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
////		} catch (DisabledException e) {
////			throw new Exception("USER_DISABLED", e);
////		} catch (BadCredentialsException e) {
////			throw new Exception("INVALID_CREDENTIALS", e);
////		}
////	}
//	
//	
//	
//
//	
//}