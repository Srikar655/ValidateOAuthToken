package com.iss.configs;


import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.iss.Services.Connectedusers;
import com.iss.models.ConnectUsers;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {

    private final Connectedusers connectedUsersRegistry;
    
    public WebSocketInterceptor(Connectedusers connectedUsersRegistry)
    {
    	this.connectedUsersRegistry=connectedUsersRegistry;
    }
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if ("CONNECT".equals(accessor.getCommand() + "")) {
            String userId = accessor.getFirstNativeHeader("userId");
            String sessionId=accessor.getSessionId();
            if (userId != null && sessionId!=null) {
            	
                ConnectUsers connectUsers=ConnectUsers.builder().userId(userId).sessionId(sessionId).build();
                this.connectedUsersRegistry.addUser(connectUsers);
            }
        }

        if ("DISCONNECT".equals(accessor.getCommand() + "")) {
        	String sessionId = accessor.getSessionId();
            if (sessionId != null) {
            	this.connectedUsersRegistry.removeBySessionId(sessionId);
                System.out.println("User disconnected: " + sessionId);
            }
        }

        return message;
    }
}
