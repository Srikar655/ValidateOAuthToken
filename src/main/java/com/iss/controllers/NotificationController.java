package com.iss.controllers;

import java.util.List;
import java.util.Stack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.Services.UserNotificationService;

@RestController
@RequestMapping("/api/user-notification")
public class NotificationController {
	private final UserNotificationService userNotificationService;
	public NotificationController(UserNotificationService userNotificationService)
	{
		this.userNotificationService=userNotificationService;
	}
	
	@GetMapping("/get-notifications")
	public List<String> getUnReadNotifications(String userId)
	{
		try {
			return this.userNotificationService.findAllByUserId(userId);
		}
		catch(Exception ex)
		{
			Stack s;
			ex.printStackTrace();
			return null;
			
		}
	}
	
}
