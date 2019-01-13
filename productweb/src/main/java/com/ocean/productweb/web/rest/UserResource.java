package com.ocean.productweb.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocean.productweb.service.UserService;
import com.ocean.productweb.service.dto.UserDTO;

@RestController("users")
public class UserResource {
	private final UserService userService;

	@Autowired
	public UserResource(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		
		return new ResponseEntity<>(this.userService.getAllUser(), HttpStatus.OK);
	}

}
