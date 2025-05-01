package com.example.demo.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.MailService;
import com.example.demo.Service.UserService;
import com.example.demo.model.Mail;
import com.example.demo.model.Users;
import com.example.demo.response.SuccessResponse;

@RestController
@RequestMapping("management/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	MailService mailService;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/addOrUpdateUser")
	public SuccessResponse addOrUpdateUser(@RequestBody Users users) {
		return userService.addOrUpdateUser(users);
	}

	@PostMapping("/login")
	public SuccessResponse loginUser(@RequestBody Users users) {
		return userService.login(users);
	}

	@GetMapping("/getById/{id}")
	public SuccessResponse getById(@PathVariable Long id) {
		return userService.getById(id);
	}

	@DeleteMapping("/deleteUser/{id}")
	public SuccessResponse deleteUser(@PathVariable Long id) {
		return userService.deleteUser(id);
	}

	@GetMapping("/getAll")
	public SuccessResponse getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/updatePassword")
	public SuccessResponse updatePassword(@RequestBody Users users) {
		return userService.updatePass(users);
	}

	@PostMapping("/sendEmail")
	public SuccessResponse sendEmail(@RequestBody Mail info) {
		Mail mail = new Mail();
		SuccessResponse response = new SuccessResponse();
		Optional<Users> byEmail = userRepository.findByEmail(info.getMailTo());
		if (byEmail.isPresent()) {
			int randomNumber = (int) (Math.random() * 900000) + 100000;
			mail.setMailFrom("geniushr25@gmail.com");
			mail.setMailTo(info.getMailTo());
			mail.setMailSubject("OTP for forget Password");
			String mailContent = String.format("Dear %s,\n\n"
					+ "We received a request to reset your password for your account associated with this email address. "
					+ "Please use the following One-Time Password (OTP) to reset your password:\n\n" + "OTP: %d\n\n"
					+ "If you did not request a password reset, please ignore this email or contact support if you have questions.\n\n"
					+ "Thank you,\n" + "Genius Infotech Team", byEmail.get().getName(), randomNumber);
			mail.setMailContent(mailContent);
			mailService.sendEmail(mail);
			response.sendEmailSuccessfully(randomNumber);
			return response;
		} else {
			response.emailNotSend();
			return response;
		}
	}

}
