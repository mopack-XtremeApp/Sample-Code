package com.assignment.security.service;

import com.assignment.security.User;

/**
 * It is the Service Layer for the User CRUD operations. Here all the services
 * of Users were exposed.
 * 
 * @author Moti Prajapati
 * @since 21.05.2012
 * 
 */
public interface IUserAccountService {

	/**
	 * The method is responsible for adding new user or registering new user
	 * 
	 * @param transientUser
	 * @return
	 * @throws Exception
	 */
	public Long addUser(User transientUser) throws Exception;

	/**
	 * The method is responsible for updating existing user information
	 * 
	 * @param detachedUser
	 * @throws Exception
	 */
	public void updateUser(User detachedUser) throws Exception;

	/**
	 * The method is responsible for deleting existing user.
	 * 
	 * @param userToDelete
	 * @throws Exception
	 */
	public void deleteUser(User userToDelete) throws Exception;

	/**
	 * This method returns the user with username passed in parameter
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User findByUserName(String username) throws Exception;

}
