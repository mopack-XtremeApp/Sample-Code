package com.assignment.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <code>Permission</code> class is a JPA Entity for system permissions.
 * 
 * 
 * @author Moti Prajapati
 * @since 21.05.2012
 * 
 */
@XmlRootElement
@Entity(name = "T_PERMISSION_MASTER")
public class Permission implements Serializable{

	/**
	 * Id of the user
	 */
	private Long id;

	/**s
	 * Name of the user. It must be unique.
	 */
	private String permission;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Column
	public String getPermission() {
		return permission;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(obj, this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
