package com.iss.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws/admin").setAllowedOrigins("http://localhost:4200").withSockJS();
    registry.addEndpoint("/ws/user").setAllowedOrigins("http://localhost:4200").withSockJS();
  }
  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
      registration.setMessageSizeLimit(2048 * 2048); // default : 64 * 1024
      registration.setSendTimeLimit(2048 * 2048); // default : 10 * 10000
      registration.setSendBufferSizeLimit(2048 * 2048); // default : 512 * 1024

  }
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
      registration.interceptors(new WebSocketInterceptor());
  }
}