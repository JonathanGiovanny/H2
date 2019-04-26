package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.security.UserDTO;

public interface UserService {
  
  public List<UserDTO> getUsers(Pageable pageable);

  public UserDTO getUser(Long id);

  public UserDTO getUserByUsername(String username);

  public Long registerUser(UserDTO user);
}
