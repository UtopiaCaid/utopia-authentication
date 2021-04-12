package com.caid.utopia.springsecurity.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import com.caid.utopia.JWT.JwtAuthenticationEntryPoint;
import com.caid.utopia.JWT.JwtRequestFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	DataSource dataSource;

	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
	

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	    auth
	    .jdbcAuthentication()
        .dataSource(dataSource)

        .usersByUsernameQuery("select username, password, 'true' as enabled from tbl_accounts where username = ?")
        .authoritiesByUsernameQuery(
                "SELECT u.username, r.role_type " +
                "FROM tbl_account_roles r, tbl_accounts u " +
                "WHERE u.username = ? " +
                "AND u.role_id = r.role_id"
            )
        .passwordEncoder(new BCryptPasswordEncoder())
        ;
	 
	    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	      
	  
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

        
        
      
        
		
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/public").permitAll()
		.antMatchers("/authenticated").authenticated()		
		.antMatchers("/user").hasAnyRole("ADMIN", "USER")
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/getSecurityAccount").authenticated()
		.antMatchers(HttpMethod.POST,"/Authenticate").permitAll()
		.antMatchers(HttpMethod.GET, "/Authentication").authenticated()
		.and()
		.logout()
		.logoutUrl("/logout").logoutSuccessUrl("/login")
		.clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID")
		 .and()
         .exceptionHandling()
             .accessDeniedPage("/403")
             
             .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
             .and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		


	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	

	  
	  @Bean
	    public JwtRequestFilter authenticationTokenFilterBean() {
	        return new JwtRequestFilter();
	    }

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	

	
	
	
	
	
}