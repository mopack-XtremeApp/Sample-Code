package com.assignment.security.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

public class UserAuthenticationTest extends BaseJUnitTest {

	@Test
	public void authenticateUser() throws Exception {

		super.mockMvc
				.perform(
						post("/authenticate").param("username", "ADMIN")
								.param("password", "admin123")

								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("SUPER USER"));

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
