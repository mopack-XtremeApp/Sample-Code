package com.assignment.security.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.http.MediaType;

public class UserOperationsTest extends BaseJUnitTest {

	@Test
	public void addUser() throws Exception {
		// Here we are adding new user in the system
		mockMvc.perform(
				post("/secure/user/addUser").param("username", "username1")
						.param("firstName", "firstName1")
						.param("lastName", "lastName1")
						.param("emailAddress", "username1@somedomain.com")
						.param("password", "abcd1234")

						.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("Success"));

	}

	@Test
	public void validateAddedUser() throws Exception {
		// Here we are validating the user which is added in above method
		mockMvc.perform(
				get("/secure/user/{username}", "username1").accept(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("username").value("username1"));

	}

	@Test
	public void addMoreUser() throws Exception {

		mockMvc.perform(
				post("/secure/user/addUser").param("username", "username2")
						.param("firstName", "firstName2")
						.param("lastName", "lastName2")
						.param("emailAddress", "username2@somedomain.com")
						.param("password", "pqrs1234")

						.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("Success"));

	}

	@Test
	public void updateUser() throws Exception {
		// Here we are changing the value of firstName to updateFirstName for
		// user username1
		mockMvc.perform(
				post("/secure/user/updateUser").param("username", "username1")
						.param("firstName", "updatedFirstName")
						.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("Success"));

	}

	@Test
	public void validateUpdateOperation() throws Exception {
		// here we validate the firstName of username1 user should be
		// updatedFirstName
		mockMvc.perform(
				get("/secure/user/{username}", "username1").accept(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("firstName").value("updatedFirstName"));

	}

	@Test
	public void updateUserWhichDoesNotExistInTheSystem() throws Exception {
		mockMvc.perform(
				post("/secure/user/updateUser").param("username", "username5")
						.param("firstName", "updatedFirstName")
						.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("Fail"));

	}

	@Test
	public void deleteUser() throws Exception {
		// Here we are deleting user username2
		mockMvc.perform(
				delete("/secure/user/deleteUser/username2").accept(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("Success"));

	}

	@Test
	public void deleteUserWhichDoesNotExistInTheSystem() throws Exception {
		// Here we are deleting same user in above method. The response should
		// be Fail because the user does not exist
		mockMvc.perform(
				delete("/secure/user/deleteUser/username2").accept(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("Fail"));

	}

	@AfterClass
	public static void destroy() throws Exception {
		mockMvc.perform(
				delete("/secure/user/deleteUser/username1").accept(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").value("Success"));
	}

}
