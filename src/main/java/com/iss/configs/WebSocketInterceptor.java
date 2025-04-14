package com.iss.configs;


import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {

    public static Set<String> connectedUserIds = ConcurrentHashMap.newKeySet();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if ("CONNECT".equals(accessor.getCommand() + "")) {
            String userId = accessor.getFirstNativeHeader("userId");
            if (userId != null) {
                connectedUserIds.add(userId);
                System.out.println("User connected: " + userId);
            }
        }

        if ("DISCONNECT".equals(accessor.getCommand() + "")) {
            String userId = accessor.getUser() != null ? accessor.getUser().getName() : null;
            if (userId != null) {
                connectedUserIds.remove(userId);
                System.out.println("User disconnected: " + userId);
            }
        }

        return message;
    }
}
