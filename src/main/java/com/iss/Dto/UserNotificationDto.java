package com.iss.Dto;

import com.iss.models.Notifications;
import com.iss.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserNotificationDto {
	private int id;
	
	private User user;
	
	private Notifications notification;
	
    private boolean isRead = false;
}
