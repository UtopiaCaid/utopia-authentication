///////
package com.caid.utopia.JWT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.caid.utopia.repo.AccountRepo;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	AccountRepo accountsRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		
		final String requestTokenHeader = request.getHeader("Authorization");
			
		
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && !requestTokenHeader.equals("Bearer null") && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		}
		else if(requestTokenHeader == null) {
			logger.warn("JWT Token is NUll");
		}
		else if(!requestTokenHeader.equals("Bearer null")) {
			//whenever a route is taken and the user isn't logged in
//			logger.warn("JWT Token is NUll");
		}
		else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				
				  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtTokenUtil.getAuthentication(jwtToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
				  usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                logger.info("authenticated user " + username + ", setting security context");

				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
		}
		chain.doFilter(request, response);
	}

}
