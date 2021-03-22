package com.caid.utopia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class UtopiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtopiaApplication.class, args);
	}

}
