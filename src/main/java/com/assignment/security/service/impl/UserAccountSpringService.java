package com.assignment.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.security.ApplicationConstant;
import com.assignment.security.Permission;
import com.assignment.security.User;
import com.assignment.security.repository.UserRepository;
import com.assignment.security.service.IUserAccountService;

/**
 * It is the spring service implementation of the {@link IUserAccountService}
 * interface.
 * 
 * @author Moti Prajapati
 * @since 21.05.2012
 * 
 */
@Service("userAccountSpringService")
@Transactional
public class UserAccountSpringService implements IUserAccountService {

	private static Logger logger = Logger
			.getLogger(UserAccountSpringService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SaltSource reflectionSaltSource;

	@Autowired
	private PasswordEncoder md5PasswordEncoder;

	@PostConstruct
	public void init() {
		addDefaultSuperAdminUser();
	}

	/**
	 * {@inheritDoc}
	 */
	public Long addUser(User transientUser) throws Exception {
		logger.debug("A request for addUser for username "
				+ transientUser.getUsername());
		User persistedInstance = userRepository.save(transientUser);
		logger.debug("Successfully completed the request for addUser for username "
				+ transientUser.getUsername());
		return persistedInstance.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateUser(User detachedUser) throws Exception {
		logger.debug("A request for updateUser for username "
				+ detachedUser.getUsername());
		userRepository.save(detachedUser);
		logger.debug("Successfully completed the request for updateUser for username "
				+ detachedUser.getUsername());

	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteUser(User userToDelete) throws Exception {
		logger.debug("A request for deleteUser for username "
				+ userToDelete.getUsername());
		userRepository.delete(userToDelete);
		logger.debug("Successfully completed the request for deleteUser for username "
				+ userToDelete.getUsername());

	}

	/**
	 * {@inheritDoc}
	 */
	public User findByUserName(String username) throws Exception {
		logger.debug("A request for findUserByUserName for username "
				+ username);
		List<User> user = this.userRepository.findByUsername(username);
		if (user == null || user.isEmpty()) {
			logger.info("No user is associated with username " + username);
			return null;
		}
		logger.debug("Successfully completed the request for findUserByUserName for username "
				+ username);
		return user.get(0);
	}

	/**
	 * Adding default ADMIN user
	 */
	private void addDefaultSuperAdminUser() {
		try {
			User adminUser = findByUserName(ApplicationConstant.DEFAULT_ADMIN_USERNAME);
			if (adminUser == null) {
				adminUser = new User();
				adminUser
						.setEmailAddress(ApplicationConstant.DEFAULT_ADMIN_EMAIL);
				adminUser
						.setFirstName(ApplicationConstant.DEFAULT_ADMIN_FIRST_NAME);
				adminUser
						.setLastName(ApplicationConstant.DEFAULT_ADMIN_LAST_NAME);
				adminUser
						.setUsername(ApplicationConstant.DEFAULT_ADMIN_USERNAME);
				// Creating dynamic salt
				Object dynamicSalt = this.reflectionSaltSource
						.getSalt(adminUser);
				String password = this.md5PasswordEncoder
						.encodePassword(
								ApplicationConstant.DEFAULT_ADMIN_PASSWORD,
								dynamicSalt);
				adminUser.setPassword(password);
				Permission superUserPermission = new Permission();
				superUserPermission
						.setPermission(ApplicationConstant.DEFAULT_ADMIN_USER_ROLE);
				adminUser.getPermissions().add(superUserPermission);
				addUser(adminUser);
				logger.info("Admin user added successfully ");
			}
		} catch (Throwable throwable) {
			logger.error(
					"Error while adding ADMIN SUPER user "
							+ throwable.getMessage(), throwable);
		}
	}

	@Override
	public List<User> findAll() {
		logger.debug("A request for findAll");
		Iterable<User> userIter = this.userRepository.findAll();
		List<User> systemUsers = new ArrayList<User>();
		for (User user : userIter) {
			systemUsers.add(user);
		}
		if (systemUsers == null || systemUsers.isEmpty()) {
			logger.info("No users exist in the system");
			return null;
		}
		logger.debug("Successfully completed the request for findAll");
		return systemUsers;
	}

}
