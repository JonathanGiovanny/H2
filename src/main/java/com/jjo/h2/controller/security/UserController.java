package com.jjo.h2.controller.security;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.services.security.UserService;
import com.jjo.h2.utils.Constants;

@RestController
@RequestMapping(Constants.APP_NAME + "/security")
public class UserController {

  @Autowired
  private UserService userService;
  
  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>> getUsers(Pageable pageable) {
    return ResponseEntity.ok(userService.getUsers(pageable));
  }

  @GetMapping("/users/{username}")
  public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
    return ResponseEntity.ok(userService.getUserByUsername(username));
  }

  @PostMapping("/users")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
    return ResponseEntity.created(URI.create(userService.registerUser(user).toString())).build();
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
    user.setId(id);
    return ResponseEntity.ok(userService.updateUser(user));
  }
}
