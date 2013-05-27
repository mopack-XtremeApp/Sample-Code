package com.assignment.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <code>User</code> class is a Domain Entity, it encapsulate all information
 * about the User that helps in creating a new User account or User Registration
 * process
 * 
 * Also, in future it can be a ORM an Entity for persisting user information in
 * SQL or NoSQL Datastore.
 * 
 * @author Moti Prajapati
 * @since 21.05.2012
 * 
 */

@XmlRootElement
@Entity(name = "T_USR_MASTER")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8475909709894658713L;

	/**
	 * Id of the user
	 */
	private Long id;

	/**
	 * Name of the user. It must be unique.
	 */
	private String username;

	/**
	 * Password corresponding to the user, it must be alphanumeric with special
	 * characters
	 */
	private String password;

	/**
	 * First Name provided by the user
	 */
	private String firstName;
	/**
	 * Last Name provided by the user
	 */
	private String lastName;
	/**
	 * Valid Email Address provided by the user
	 */
	private String emailAddress;

	private List<Permission> permissions = new ArrayList<Permission>();

	public User() {
		addDefaultPermission();
	}

	/**
	 * The method is responsible for adding default permission to the System
	 * User
	 */
	@Transient
	private void addDefaultPermission() {
		Permission permission = new Permission();
		permission.setPermission("ROLE_USER");
		this.permissions.add(permission);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "T_USR_PERMISSIONS", joinColumns = { @JoinColumn(name = "userId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "permissionId", nullable = false, updatable = false) })
	public List<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(obj, this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	// Below mentioned features are not enabled, hence providing default values
	// for it
	@Transient
	public boolean isEnabled() {
		return true;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * This method is responsible for providing all authorities corresponding to
	 * the user
	 */
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final Permission permission : permissions) {
			GrantedAuthority authority = new SimpleGrantedAuthority(
					permission.getPermission());
			authorities.add(authority);
		}
		return authorities;
	}

}
