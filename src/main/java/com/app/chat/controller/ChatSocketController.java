package com.app.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.app.chat.domain.vo.ChatMessageVO;
import com.app.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {
	
	private final ChatService chatService;
	private final SimpMessagingTemplate messagingTemplate;
	
	// 클라이언트 -> 서버 메세지 전송
	@MessageMapping("/chat.send")
	public void sendMessage(@Payload ChatMessageVO chatMessageVO) {
		
		System.out.println("roomId : " + chatMessageVO.getChatRoomId());
		System.out.println("userId : " + chatMessageVO.getUserId());
		System.out.println("content : " + chatMessageVO.getContent());
		
		// DB저장 + 검증
		chatService.sendMessage(chatMessageVO);
		
		// 해당 방 구독자들에게 브로드캐스트
		messagingTemplate.convertAndSend(
					"/topic/room/" + chatMessageVO.getChatRoomId(),
					chatMessageVO
				);
	}
	
}
