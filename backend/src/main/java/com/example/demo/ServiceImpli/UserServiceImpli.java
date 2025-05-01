package com.example.demo.ServiceImpli;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.UserDTO;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.model.Users;
import com.example.demo.response.SuccessResponse;
import com.example.demo.utility.Utility;

@Service
public class UserServiceImpli implements UserService {

	@Autowired
	UserRepository userRepository;

	SuccessResponse response = new SuccessResponse();

	@Autowired
	Utility utility;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public SuccessResponse addOrUpdateUser(Users users) {
		if (users == null) {
			response.nullData();
			return response;
		}
		if (users.getMobile_number() == null || users.getPassword() == null || users.getName() == null) {
			response.nullData();
			return response;
		}

		// Check for duplicate mobile number
		Optional<Users> existingUserByMobile = userRepository.findByMobileNumber(users.getMobile_number());
		if (existingUserByMobile.isPresent() && !existingUserByMobile.get().getId().equals(users.getId())) {
			response.duplicateMobileNumber();
			return response;
		}

		// Check for duplicate email
		Optional<Users> existingUserByEmail = userRepository.findByEmail(users.getEmail());
		if (existingUserByEmail.isPresent() && !existingUserByEmail.get().getId().equals(users.getId())) {
			response.duplicateEmail();
			return response;
		}
		users.setPassword(utility.encryptText(users.getPassword()));

		// Check if the user ID is present
		if (users.getId() != null) {
			Optional<Users> existingUser = userRepository.findById(users.getId());

			if (existingUser.isPresent()) {
				// Update existing user
				Users userToUpdate = existingUser.get();
				userToUpdate.setName(users.getName());
				userToUpdate.setMobile_number(users.getMobile_number());
				userToUpdate.setEmail(users.getEmail());
				userToUpdate.setPassword(users.getPassword()); // Encrypted password
				userRepository.save(userToUpdate);
				response.userUpdated(userToUpdate);
				UserDTO map = modelMapper.map(userToUpdate, UserDTO.class);
				response.userUpdated(map);
				return response;

			} else {

				// Check if there's already a user in the database
				long userCount = userRepository.count();
				if (userCount >= 2) {
					response.maxUserLimitReached();
					return response;
				}
				// User ID provided but not found in DB, treating as a new user
				userRepository.save(users);
				UserDTO map = modelMapper.map(users, UserDTO.class);
				response.userAdded(map);
				return response;
			}
		} else {
			userRepository.save(users);
			UserDTO map = modelMapper.map(users, UserDTO.class);
			response.userAdded(map);
			return response;
		}

	}

	@Override
	public SuccessResponse getAllUsers() {
		List<Users> findAll = userRepository.findAll();
		if (findAll.isEmpty()) {
			response.userNotFound();
			return response;
		}
		List<UserDTO> userDTO = findAll.stream().map(users -> modelMapper.map(users, UserDTO.class))
				.collect(Collectors.toList());
		response.users(userDTO);
		return response;
	}

	@Override
	public SuccessResponse getById(Long id) {
		Optional<Users> findbyId = userRepository.findbyUserId(id);
		if (!findbyId.isPresent()) {
			response.userNotFound();
			return response;
		}
		UserDTO map = modelMapper.map(findbyId.get(), UserDTO.class);
		response.users(map);
		return response;
	}

	@Override
	public SuccessResponse deleteUser(Long id) {
		Optional<Users> findById = userRepository.findById(id);
		if (!findById.isPresent()) {
			response.userNotFound();
			return response;
		}
		userRepository.deleteById(id);
		response.userDeleted(id);
		return response;
	}

	@Override
	public SuccessResponse login(Users users) {
		if (users.getMobile_number() == null || users.getPassword() == null) {
			response.nullData();
			return response;
		}
		String mobile_number = users.getMobile_number();
		String password = users.getPassword();
		Optional<Users> findByMobileNumber = userRepository.findByMobileNumber(mobile_number);
		if (!findByMobileNumber.isPresent()) {
			response.invalidMobile();
			return response;
		}
		Users users2 = findByMobileNumber.get();
		// Decrypt the stored password for comparison
		String decryptedPassword = utility.decryptText(users2.getPassword());
		if (password.equals(decryptedPassword)) {
			UserDTO map = modelMapper.map(users2, UserDTO.class);
			response.loginSuccesfully(map);
			return response;
		} else {
			response.invalidPassword();
			return response;
		}
	}

	@Override
	public SuccessResponse updatePass(Users users) {
		if (users.getPassword() == null) {
			response.nullData();
			return response;
		}
		Optional<Users> findById = userRepository.findById(users.getId());
		if (!findById.isPresent()) {
			response.userNotFound();
			return response;
		}
		Users users2 = findById.get();
		users2.setPassword(users.getPassword());
		userRepository.save(users2);
		response.passwordUpdated();
		return response;
	}

}
