package com.jjo.h2.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.services.security.UserService;
import com.jjo.h2.utils.Constants;
import com.jjo.h2.utils.ExceptionUtils;
import com.jjo.h2.utils.HException;

@RestController
@RequestMapping(Constants.APP_NAME + "/security")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		ResponseEntity<?> response = null;
		try {
			response = new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		ResponseEntity<?> response = null;
		try {
			response = new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@PostMapping("/user")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		try {
			userService.registerUser(user);
			response = new ResponseEntity<>(HttpStatus.CREATED);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}
}
