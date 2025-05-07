package com.iss.Services;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.iss.models.ConnectUsers;

@Service
public class Connectedusers {
	private final Set<ConnectUsers> connectedUsers;
	
	public Connectedusers()
	{
		this.connectedUsers=ConcurrentHashMap.newKeySet();
	}
	public void addUser(ConnectUsers user) {
        connectedUsers.add(user);
    }

    public void removeBySessionId(String sessionId) {
        connectedUsers.removeIf(user -> user.getSessionId().equals(sessionId));
    }

    public Set<ConnectUsers> getAllUsers() {
        return connectedUsers;
    }

    public ConnectUsers getByUserId(String userId) {
        return connectedUsers.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public ConnectUsers getBySessionId(String sessionId) {
        return connectedUsers.stream()
                .filter(user -> user.getSessionId().equals(sessionId))
                .findFirst()
                .orElse(null);
    }
}
