package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Users;
import com.example.demo.response.SuccessResponse;

@Service
public interface UserService {

	public SuccessResponse addOrUpdateUser(Users users);
	
	public SuccessResponse getAllUsers();

	public SuccessResponse getById(Long id);

	public SuccessResponse deleteUser(Long id);

	public SuccessResponse login(Users users);

	public SuccessResponse updatePass(Users users);


}
