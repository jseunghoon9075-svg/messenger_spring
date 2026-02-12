package com.app.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.app.chat.handler.ChatHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		registry.addEndpoint("/ws")
			.setAllowedOriginPatterns("*") // 프론트 연동대비
			.withSockJS(); // SockJS 쓸거면 반드시 필요
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		registry.enableSimpleBroker("/topic"); // 구독 prefix
		registry.setApplicationDestinationPrefixes("/app"); // 발행 prefix
	}
	
	
	
}
