package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.security.UserDTO;

public interface UserService {

  /**
   * Get all users
   * @param pageable
   * @return
   */
  List<UserDTO> getUsers(Pageable pageable);

  /**
   * Get user by id
   * @param id
   * @return
   */
  UserDTO getUser(Long id);

  /**
   * Get user by its username (should be unique)
   * @param username
   * @return
   */
  UserDTO getUserByUsername(String username);

  /**
   * Register a user
   * @param user
   * @return
   */
  Long registerUser(UserDTO user);

  /**
   * Update user info
   * @param user
   * @return
   */
  UserDTO updateUser(UserDTO user);
}
