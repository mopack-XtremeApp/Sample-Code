package com.assignment.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.assignment.security.ApplicationConstant;
import com.assignment.security.User;
import com.assignment.security.controller.bo.HttpOperationResult;
import com.assignment.security.service.IUserAccountService;

/**
 * This is spring controller class for user authentication purpose and for
 * forget password functionality.
 * 
 * @author moti.prajapati
 * 
 */
@Controller
public class UserAuthenticationController {

	@Autowired
	private IUserAccountService userAccountSpringService;

	@Autowired
	private SaltSource reflectionSaltSource;

	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;

	private static Logger logger = Logger
			.getLogger(UserAccountController.class);

	/**
	 * This method authenticate user and create security context for that user.
	 * The authentication parameters should be in the request parameter.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = {
			"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	HttpOperationResult authenticateUser(HttpServletRequest request) {
		HttpOperationResult result = new HttpOperationResult();
		result.setOperation("authenticateUser");
		result.setMessage("Authentication Failed");
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					username, password);

			// generate session if one doesn't exist
			request.getSession();

			token.setDetails(new WebAuthenticationDetails(request));
			Authentication authenticatedUser = authenticationManager
					.authenticate(token);

			SecurityContextHolder.getContext().setAuthentication(
					authenticatedUser);

			if (username
					.equalsIgnoreCase(ApplicationConstant.DEFAULT_ADMIN_USERNAME)) {
				result.setMessage(ApplicationConstant.ADMIN_USR_LOGIN_INDICATOR);
			} else {
				result.setMessage(ApplicationConstant.NORMAL_USR_LOGIN_INDICATOR);
			}

		} catch (Throwable throwable) {
			logger.error(throwable.getMessage(), throwable);

		}

		return result;
	}

	/**
	 * The method is responsible to logged out current user from the system.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = {
			"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	HttpOperationResult logout(HttpServletRequest request,
			HttpServletResponse response) {
		HttpOperationResult result = new HttpOperationResult();
		result.setOperation("logout");
		result.setMessage("Logout Failed");
		try {

			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response,
						auth);
				// new PersistentTokenBasedRememberMeServices().logout(request,
				// response, auth);
			}
			result.setMessage("Success");

		} catch (Throwable throwable) {
			logger.error(throwable.getMessage(), throwable);
			result.setMessage("Failed");
		}

		return result;
	}

	/**
	 * This method will be called for forget password request.
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/forgetPassword/{username}", method = RequestMethod.POST, produces = {
			"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	HttpOperationResult forgetPassword(@PathVariable String username) {
		HttpOperationResult result = new HttpOperationResult();
		result.setOperation("forgetPassword");
		result.setMessage("User does not exist");
		try {

			User existingUser = userAccountSpringService
					.findByUserName(username);
			if (existingUser == null) {
				throw new Exception("User does not exist with username "
						+ username);
			}

			// Send email to user email address with new password
			result.setMessage("New password has been sent to your email address");

			return result;
		} catch (Throwable throwable) {
			logger.error(throwable.getMessage(), throwable);

		}

		return result;
	}

}
