package com.caid.utopia;
//

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UtopiaApplication.class})
@WebAppConfiguration
public abstract class UtopiaApplicationTests {

	protected MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	WebApplicationContext webApplicationContext;

	
	
	protected void setUp() {
	    mvc = MockMvcBuilders
	            .webAppContextSetup(webApplicationContext)
	            .apply(springSecurity())
	            .build();
	}



	
}
