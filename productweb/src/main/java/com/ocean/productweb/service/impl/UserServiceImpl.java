package com.ocean.productweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ocean.productweb.service.UserService;
import com.ocean.productweb.service.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public List<UserDTO> getAllUser() {
		List<UserDTO> userDtos = new ArrayList<UserDTO>();

		userDtos.add(new UserDTO(1, "duongnd2016@gmail.com", "Nguyen Dai Duong"));
		userDtos.add(new UserDTO(2, "oceannguyen@gmail.com", "Nguyen Dai Duong"));
		userDtos.add(new UserDTO(3, "oceanoppa@gmail.com", "Nguyen Dai Duong"));

		return userDtos;
	}

}
