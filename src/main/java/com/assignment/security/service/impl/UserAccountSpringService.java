package com.assignment.security.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired()
	private PasswordEncoder md5PasswordEncoder;

	@PostConstruct
	public void init() {
		addDefaultSuperAdminUser();
	}

	public Long addUser(User transientUser) throws Exception {
		logger.debug("A request for addUser for username "
				+ transientUser.getUsername());
		User persistedInstance = userRepository.save(transientUser);
		logger.debug("Successfully completed the request for addUser for username "
				+ transientUser.getUsername());
		return persistedInstance.getId();
	}

	public void updateUser(User detachedUser) throws Exception {
		logger.debug("A request for updateUser for username "
				+ detachedUser.getUsername());
		userRepository.save(detachedUser);
		logger.debug("Successfully completed the request for updateUser for username "
				+ detachedUser.getUsername());

	}

	public void deleteUser(User userToDelete) throws Exception {
		logger.debug("A request for deleteUser for username "
				+ userToDelete.getUsername());
		userRepository.delete(userToDelete);
		logger.debug("Successfully completed the request for deleteUser for username "
				+ userToDelete.getUsername());

	}

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
			User adminUser = findByUserName("ADMIN");
			if (adminUser == null) {
				adminUser = new User();
				adminUser.setEmailAddress("admin@xyz.com");
				adminUser.setFirstName("System Administrator");
				adminUser.setLastName("SUPER USER");
				adminUser.setUsername("ADMIN");
				// Creating dynamic salt
				Object dynamicSalt = this.reflectionSaltSource
						.getSalt(adminUser);
				String password = this.md5PasswordEncoder.encodePassword(
						"admin123", dynamicSalt);
				adminUser.setPassword(password);
				Permission superUserPermission = new Permission();
				superUserPermission.setPermission("ROLE_SUPER_USER");
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

}
