package com.iss.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iss.Repos.UserNotifcationRepository;
import com.iss.models.UserNotifications;

@Service
public class UserNotificationService {

    private final UserNotifcationRepository notificationRepository;

    public UserNotificationService(UserNotifcationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public UserNotifications add(UserNotifications notification) {
        try {
            return notificationRepository.save(notification);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding UserNotification", ex);
        }
    }

    @Transactional
    public List<String> findAllByUserId(String userId) {
        try {
            Optional<List<UserNotifications>> optional= notificationRepository.findByUserId(userId);
            if(optional.isPresent())
            {
            	List<UserNotifications> list= optional.get();
            	List<String> messages=list.stream().map(n->{
            		n.setRead(true);
            		return n.getNotification().getMessage();
            	}).collect(Collectors.toList());
            	return messages;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching notifications for user " + userId, ex);
        }
    }

    public void deleteNotification(int id) {
        try {
            if (notificationRepository.existsById(id)) {
                notificationRepository.deleteById(id);
            } else {
                throw new RuntimeException("Notification with id " + id + " not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting notification with id " + id, ex);
        }
    }

    public UserNotifications find(int id) {
        try {
            Optional<UserNotifications> notificationOpt = notificationRepository.findById(id);
            return notificationOpt.orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching notification with id " + id, ex);
        }
    }
}
