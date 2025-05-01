package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.NotificationService;
import com.example.demo.response.SuccessResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("management/v1/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/getAllNotifications")
	public SuccessResponse getUnreadNotifications() {
		return notificationService.getAllNotifications();
	}

	@PostMapping("/read/{id}")
	public SuccessResponse markAsRead(@PathVariable Long id) {
		return notificationService.markNotificationAsRead(id);
	}
}
