package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.model.security.User;

public interface UserService {

  Boolean availableUsernameOrEmail(String usernameOrEmail);

  Boolean availableUsernameOrEmail(String username, String email);

  List<User> getUsers(Pageable pageable);

  Long registerUser(User user);

  User updateUser(Long id, User user);
}
