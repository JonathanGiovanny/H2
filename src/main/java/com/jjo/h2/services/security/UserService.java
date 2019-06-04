package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.security.SingUpDTO;
import com.jjo.h2.dto.security.UserDTO;

public interface UserService {

  /**
   * Validates that the username and / or email will be unique
   * @param usernameOrEmail
   * @return
   */
  Boolean availableUsernameOrEmail(String usernameOrEmail);
  
  /**
   * Get all users
   * 
   * @param pageable
   * @return
   */
  List<UserDTO> getUsers(Pageable pageable);

  /**
   * Register a user
   * 
   * @param user sing up form data
   * @return
   */
  Long registerUser(SingUpDTO user);

  /**
   * Update user info
   * 
   * @param id
   * @param user
   * @return
   */
  UserDTO updateUser(Long id, UserDTO user);
}
