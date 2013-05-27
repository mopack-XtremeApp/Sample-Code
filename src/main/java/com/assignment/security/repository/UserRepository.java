package com.assignment.security.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.assignment.security.User;

/**
 * This is spring data jpa repository interface for User domain class
 * 
 * @author moti.prajapati
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * This mehod returns User for the username passed as input parameter
	 * 
	 * @param userName
	 * @return
	 */
	List<User> findByUsername(String userName);

}
