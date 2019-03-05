package com.jjo.h2.dto.security;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.googlecode.jmapper.annotations.JMap;
import com.jjo.h2.model.security.Role;

import lombok.Data;

@Data
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -906906452679421031L;

	@JMap
	private Long id;

	@JMap
	private String username;

	@JMap
	private String password;

	@JMap
	private String email;

	@JMap
	private LocalDateTime createdDate;

	@JMap
	private LocalDate passwordDate;

	@JMap
	private Blob profilePic;

	@JMap
	private Set<Role> roles;
}
