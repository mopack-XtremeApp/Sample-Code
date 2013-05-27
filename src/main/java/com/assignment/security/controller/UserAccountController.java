package com.assignment.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.assignment.security.User;
import com.assignment.security.controller.bo.HttpOperationResult;
import com.assignment.security.service.IUserAccountService;

/**
 * This is Spring Controller class for the user CRUD operation. All the http
 * request for user CRUD operation will be handled in this class.
 * 
 * @author moti.prajapati
 * 
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/secure/user")
public class UserAccountController {

	private static Logger logger = Logger
			.getLogger(UserAccountController.class);

	@Autowired
	private IUserAccountService userAccountSpringService;

	@Autowired()
	private PasswordEncoder md5PasswordEncoder;

	@Autowired
	private SaltSource reflectionSaltSource;

	/**
	 * This method add new user in the system. It expects the required user
	 * attribute values from the request parameter.
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = {
			"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	HttpOperationResult addUser(HttpServletRequest request) {
		HttpOperationResult userOperationResult = new HttpOperationResult();
		userOperationResult.setOperation("addUser");
		String username = request.getParameter("username");
		userOperationResult.setMessage("Fail");
		try {

			logger.info("A request for addUser for userName " + username);

			User existingUser = userAccountSpringService
					.findByUserName(username);
			if (existingUser != null) {
				userOperationResult.setMessage("Already Exist");
				throw new Exception("User already exist with username "
						+ username);

			}
			User transientUser = new User();
			transientUser.setEmailAddress(request.getParameter("emailAddress"));
			transientUser.setFirstName(request.getParameter("firstName"));

			transientUser.setLastName(request.getParameter("lastName"));
			transientUser.setUsername(request.getParameter("username"));
			// Creating dynamic salt
			Object dynamicSalt = this.reflectionSaltSource
					.getSalt(transientUser);
			// Generating Password hash
			String password = this.md5PasswordEncoder.encodePassword(
					request.getParameter("password"), dynamicSalt);

			transientUser.setPassword(password);

			userAccountSpringService.addUser(transientUser);

			userOperationResult.setMessage("Success");

			logger.info("Successfully completed request for addUser for userName "
					+ username);

		} catch (Throwable t) {
			logger.error(
					"An error while adding user " + username + " "
							+ t.getMessage(), t);

		}
		return userOperationResult;
	}

	/**
	 * This method update the user in the system. It expects the required user
	 * attribute values from the request parameter. The request parameter must
	 * have the value for userName to update the corresponding User.
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = {
			"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	HttpOperationResult updateUser(HttpServletRequest request) {
		HttpOperationResult userOperationResult = new HttpOperationResult();
		userOperationResult.setOperation("updateUser");
		String username = request.getParameter("username");

		try {
			logger.info("A request for updateUser for userName " + username);

			User existingUser = userAccountSpringService
					.findByUserName(username);
			if (existingUser == null) {
				throw new Exception("User does not exist with username "
						+ username);
			}

			existingUser.setEmailAddress(request.getParameter("emailAddress"));
			existingUser.setFirstName(request.getParameter("firstName"));
			existingUser.setLastName(request.getParameter("lastName"));

			// Creating dynamic salt
	/*		Object dynamicSalt = this.reflectionSaltSource
					.getSalt(existingUser);

			String password = this.md5PasswordEncoder.encodePassword(
					request.getParameter("password"), dynamicSalt);
			existingUser.setPassword(password);
*/
			userAccountSpringService.updateUser(existingUser);

			userOperationResult.setMessage("Success");

			logger.info("Successfully completed request for updateUser for userName "
					+ username);

		} catch (Throwable t) {
			logger.error(
					"An error while updating user " + username + " "
							+ t.getMessage(), t);
			userOperationResult.setMessage("Fail");

		}
		return userOperationResult;
	}

	private boolean isNullOrEmpty(String value) {
		if (value == null || value.isEmpty())
			return true;
		return false;
	}

	/**
	 * This method deletes the user in the system. The user to delete should be
	 * provided as path variable.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteUser/{username}", method = RequestMethod.DELETE, produces = {
			"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	HttpOperationResult deleteUser(@PathVariable String username) {
		HttpOperationResult userOperationResult = new HttpOperationResult();
		userOperationResult.setOperation("deleteUser");
		try {
			logger.info("A request for deleteUser for userName " + username);

			User existingUser = userAccountSpringService
					.findByUserName(username);
			if (existingUser == null) {
				throw new Exception("User does not exist with username "
						+ username);
			}

			userAccountSpringService.deleteUser(existingUser);

			userOperationResult.setMessage("Success");
			logger.info("Successfully completed request for deleteUser for userName "
					+ username);

		} catch (Throwable t) {
			logger.error(
					"An error while deleting user " + username + " "
							+ t.getMessage(), t);
			userOperationResult.setMessage("Fail");

		}
		return userOperationResult;
	}

	/**
	 * This method returns the User object(corresponding to username path
	 * variable) in either JSON or XML.
	 * 
	 * @param username
	 * @return
	 */

	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = {
			"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	User getUser(@PathVariable("username") String username) {
		try {
			logger.info("A request for getUser for userName " + username);

			User existingUser = userAccountSpringService
					.findByUserName(username);
			if (existingUser == null) {
				throw new Exception("User does not exist with username "
						+ username);
			}
			logger.info("Sucessfully completed request for getUser for userName "
					+ username);

			return existingUser;
		} catch (Throwable t) {
			logger.error(
					"An error while getting user " + username + " "
							+ t.getMessage(), t);

			return null;

		}

	}

	/**
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {
			"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	java.util.List<User> findAll() {
		try {
			logger.info("A request for findAll");

			List<User> existingUsers = userAccountSpringService.findAll();
			if (existingUsers == null) {
				return new ArrayList<User>();
			}

			logger.info("Sucessfully completed request for findAll");

			return existingUsers;
		} catch (Throwable t) {
			logger.error("An error while findAll users " + t.getMessage(), t);

			return null;

		}

	}

}
