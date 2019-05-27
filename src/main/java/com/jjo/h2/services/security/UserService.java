package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import com.jjo.h2.dto.security.UserDTO;

public interface UserService {

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
   * @param user
   * @return
   */
  Long registerUser(UserDTO user);

  /**
   * Update user info
   * 
   * @param id
   * @param user
   * @return
   */
  UserDTO updateUser(Long id, UserDTO user);

  /**
   * Find user by its id
   * 
   * @param id
   * @return
   */
  UserDetails loadUserById(Long id);
}
