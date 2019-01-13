package com.ocean.productweb.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private int id;
	private String email;
	private String name;

	public UserDTO(int id, String email, String name) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
	}

	public UserDTO() {
		super();
	}

}
