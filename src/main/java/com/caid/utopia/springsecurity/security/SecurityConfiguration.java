package com.caid.utopia.springsecurity.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	    auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//        .dataSource(dataSource)
//        .usersByUsernameQuery("select username, password, role_id from tbl_accounts where role_id=?")
//        .authoritiesByUsernameQuery("select username, role_id from tbl_accounts where role_id=?")
//    ;
		//auth.jdbcAuthentication().dataSource(dataSource);
		 auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("adminpass")).roles("ADMIN")
				.and().withUser("user").password(passwordEncoder().encode("userpass")).roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
		http.authorizeRequests().antMatchers("/public").permitAll()
		.antMatchers("/authenticated").authenticated()
		.antMatchers("/user").hasAnyRole("ADMIN", "USER","1","0")
		.antMatchers("/admin").hasAnyRole("ADMIN","1")
		.and().httpBasic();
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
