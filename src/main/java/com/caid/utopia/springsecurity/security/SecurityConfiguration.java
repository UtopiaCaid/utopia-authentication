package com.caid.utopia.springsecurity.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
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
	    
	    
	    
//		 auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("adminpass")).roles("ADMIN")
//				.and().withUser("user").password(passwordEncoder().encode("userpass")).roles("USER");
	    
	    
//	   System.out.println(new BCryptPasswordEncoder().encode("userpass"));
//	   System.out.println(new BCryptPasswordEncoder().encode("adminpass"));
	   //'userpass' = $2a$10$KJivYEkQbd8ZHt/jkCiBeeuo9cU84PiK/Peq/tXdp5sp5AtaTvpQa
	   //'adminpass'= $2a$10$jpVmpP2nttJjpQiOx8PLre8l/zNxBgBLSx3mM.jn.xXE8.dt63cby
	   //System.out.println( auth.jdbcAuthentication().dataSource(dataSource).getUserDetailsService().getUsersByUsernameQuery());
	 
	  
	  
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

        
        
      
        
		
		http
		.csrf().disable()
//		.cors().disable()
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
//             .and()
//             .oauth2ResourceServer()
//             .jwt()
             .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
             .and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		;
		
		//http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
//      // .loginProcessingUrl("/login") // link to submit username-password
//      .loginPage("/login")
//      .usernameParameter("username")
//      .passwordParameter("password")
//      .defaultSuccessUrl("/user")
//      .failureUrl("/login?error").and()

	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//	    web.ignoring().antMatchers("/authenticate");                
//	}
//	
	
	
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		// configure AuthenticationManager so that it knows from where to load
//		// user for matching credentials
//		// Use BCryptPasswordEncoder
//		
//		 auth
//		    .jdbcAuthentication()
//	        .dataSource(dataSource)
//
//	        .usersByUsernameQuery("select username, password, 'true' as enabled from tbl_accounts where username = ?")
//	        .authoritiesByUsernameQuery(
//	                "SELECT u.username, r.role_type " +
//	                "FROM tbl_account_roles r, tbl_accounts u " +
//	                "WHERE u.username = ? " +
//	                "AND u.role_id = r.role_id"
//	            )
//	        .passwordEncoder(new BCryptPasswordEncoder())
//	        ;
//		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//	}
	
	
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