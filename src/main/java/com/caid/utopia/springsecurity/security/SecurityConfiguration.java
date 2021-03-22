package com.caid.utopia.springsecurity.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	DataSource dataSource;

	
	
	//SqlDataSource ds = new SqlDataSource();
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth
	    .jdbcAuthentication()
        .dataSource(dataSource)

        .usersByUsernameQuery("select username, password, 'true' as enabled from tbl_accounts where username = ?")
//        .authoritiesByUsernameQuery("select username, role from tbl_accounts where username=?")
        .authoritiesByUsernameQuery(
                "SELECT u.username, r.role_type " +
                "FROM tbl_account_roles r, tbl_accounts u " +
                "WHERE u.username = ? " +
                "AND u.role_id = r.role_id"
            )
        .passwordEncoder(new BCryptPasswordEncoder())
        ;
	 
	
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
//		 http
//         .logout(logout -> logout
//           .logoutUrl("/user")
//           .addLogoutHandler(new SecurityContextLogoutHandler())
//         );
//		http
//        .logout(logout -> logout
//          .logoutUrl("/user")
//          .addLogoutHandler((request, response, auth) -> {
//              for (Cookie cookie : request.getCookies()) {
//                  String cookieName = cookie.getName();
//                  Cookie cookieToDelete = new Cookie(cookieName, null);
//                  cookieToDelete.setMaxAge(0);
//                  response.addCookie(cookieToDelete);
//              }
//          })
//        );
//  
//		http.logout();
//		 SecurityContextHolder.clearContext();
//		 
//		 SecurityContextHolder.getContext().setAuthentication(null);
		 
		http.authorizeRequests()
		.antMatchers("/public").permitAll()
		.antMatchers("/authenticated").authenticated()		
		.antMatchers("/user").hasAnyRole("ADMIN", "USER")
		.antMatchers("/admin").hasRole("ADMIN")
		.and().httpBasic();
		
//		http.logout();
		
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	   @Bean
//	    public DataSource getDataSource() {
//	        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//	        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//	        dataSourceBuilder.url("jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//	        dataSourceBuilder.username("root");
//	        dataSourceBuilder.password("Root223");
//	        return dataSourceBuilder.build();
//	    }
	
}
