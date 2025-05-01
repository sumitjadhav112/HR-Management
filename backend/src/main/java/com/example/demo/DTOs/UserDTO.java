package com.example.demo.DTOs;

public class UserDTO {

	private Long id;
	private String name;
	private String mobile_number;
	private String email;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDTO(Long id, String name, String mobile_number, String email) {
		super();
		this.id = id;
		this.name = name;
		this.mobile_number = mobile_number;
		this.email = email;
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
