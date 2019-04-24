package com.jjo.h2.services.security;

import java.util.List;

import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.utils.HException;

public interface UserService {
	
	UserDTO getUser(Long id) throws HException;

	void registerUser(UserDTO user) throws HException;

	List<UserDTO> getUserByUsername(String username) throws HException;
}
