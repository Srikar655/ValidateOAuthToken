package com.iss.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.iss.Repos.NotificationRepository;
import com.iss.models.ConnectUsers;
import com.iss.models.Notifications;
import com.iss.models.User;
import com.iss.models.UserNotifications;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {
	private final SimpMessagingTemplate messagingTemplate;
	private final NotificationRepository repos;
	private final Connectedusers connectedUsersService;
	private final CustomUserDetailsService customUserDetailsService;
	private final UserNotificationService userNotificationService;
	
	public NotificationService(NotificationRepository repos,SimpMessagingTemplate messagingTemplate,Connectedusers connectedUsersService,CustomUserDetailsService customUserDetailsService,UserNotificationService userNotificationService) {
    	this.messagingTemplate=messagingTemplate;
        this.repos = repos;
        this.connectedUsersService=connectedUsersService;
        this.userNotificationService=userNotificationService;
        this.customUserDetailsService=customUserDetailsService;
    }
	@Async
	public void sendNotificationToAdmin(Notifications notification)
	{
		try {
			System.out.println(notification+"STARTING");
			List<User> list=this.customUserDetailsService.findByRole("ROLE_ADMIN");
			if(list!=null)
			{
				notification.setUsers(list);
				Notifications note=repos.save(notification);
				Set<ConnectUsers> connectedStreams = this.connectedUsersService.getAllUsers();
				for(User user : list)
				{
					UserNotifications usernotifications=UserNotifications.builder().user(user).notification(notification).isRead(false).build();
					UserNotifications u=userNotificationService.add(usernotifications);
					Optional<String> optionalsessionId=connectedStreams.stream().filter(c->c.getUserId().equals(user.getEmail())).findFirst().map(ConnectUsers::getSessionId);
					if(optionalsessionId.isPresent())
					{
						messagingTemplate.convertAndSend("/topic/task-result-"+user.getEmail() , note.getMessage());
						System.out.println("/topic/task-result-"+user.getEmail());
						u.setRead(true);
						this.userNotificationService.add(u);
						System.out.println("ENDED");
					}
					
					
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	@Async
	public void sendNotificationToUser(Notifications notification)
	{
		try {
			User user=this.customUserDetailsService.find(notification.getReceiver());
			if(user!=null)
			{
				notification.setUsers(List.of(user));
				Notifications note=repos.save(notification);
				ConnectUsers c=this.connectedUsersService.getByUserId(user.getEmail());
				if(c!=null)
				{
					messagingTemplate.convertAndSendToUser(c.getSessionId(), "/topic/task-result", note);
					this.repos.save(note);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
