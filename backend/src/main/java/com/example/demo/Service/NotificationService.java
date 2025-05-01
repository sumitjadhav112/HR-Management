package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.response.SuccessResponse;

@Service
public interface NotificationService {
	
	public SuccessResponse getAllNotifications();

	public SuccessResponse markNotificationAsRead(Long id);

}
