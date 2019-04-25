package com.jjo.h2.services.security;

import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.exception.HException;

public interface UserService {
	
	public UserDTO getUser(Long id) throws HException;

	public void registerUser(UserDTO user) throws HException;
}
