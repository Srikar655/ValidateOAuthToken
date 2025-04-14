package com.iss.Services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;

import com.iss.Repos.NotificationRepository;
import com.iss.models.Notifications;

public class NotificationService {
	private final SimpMessagingTemplate messagingTemplate;
	private final NotificationRepository repos;
	
	public NotificationService(NotificationRepository repos,SimpMessagingTemplate messagingTemplate) {
    	this.messagingTemplate=messagingTemplate;
        this.repos = repos;
    }
	@Async
	public void sendNotification(Notifications notification)
	{
		try {
			Notifications note=repos.save(notification);
			messagingTemplate.convertAndSend("/topic/task-result", note);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
