package com.assignment.security.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.security.service.IUserAccountService;

/**
 * This service is responsible for providing User Instance from the persistance
 * store store.
 * 
 * @author Moti Prajapti
 * 
 */
@Service
public class UserAuthenticationService implements UserDetailsService {

	private static Logger logger = Logger
			.getLogger(UserAuthenticationService.class);

	@Autowired
	private IUserAccountService userAccountSpringService;

	@Autowired()
	private PasswordEncoder md5PasswordEncoder;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserDetails userDetail = null;
		try {
			logger.info("Trying to fetch User Instance for username "
					+ username);
			userDetail = userAccountSpringService.findByUserName(username);
			if (userDetail == null) {
				throw new UsernameNotFoundException("User " + username
						+ " does not exists ");
			}
		} catch (Exception exception) {
			throw new UsernameNotFoundException(
					"Something goes wrong while authentication ", exception);
		}

		return userDetail;

	}

}
