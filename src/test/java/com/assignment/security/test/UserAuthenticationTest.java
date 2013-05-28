package com.assignment.security.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.assignment.security.ApplicationConstant;

public class UserAuthenticationTest extends BaseJUnitTest {

	@Test
	public void authenticateUser() throws Exception {

		super.mockMvc
				.perform(
						post("/authenticate").param("username", ApplicationConstant.DEFAULT_ADMIN_USERNAME)
								.param("password", ApplicationConstant.DEFAULT_ADMIN_PASSWORD)

								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value(ApplicationConstant.ADMIN_USR_LOGIN_INDICATOR));

	}

	@Test
	public void authenticateWithInvalidCredentials() throws Exception {

		mockMvc.perform(
				post("/authenticate").param("username", "rod")
						.param("password", "aaa")

						.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("Authentication Failed"));

	}

}
