package com.app.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.chat.domain.vo.ChatMessageVO;
import com.app.chat.domain.vo.ChatRoomVO;
import com.app.chat.service.ChatRoomService;
import com.app.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
	
	private final ChatService chatService;
	private final ChatRoomService chatRoomService;
	
//	메세지 전송
	@PostMapping("/message")
	public void sendMessage(@RequestBody ChatMessageVO messageVO) {
		chatService.sendMessage(messageVO);
	}
	
	@GetMapping("/rooms/{roomId}/messages")
	public List<ChatMessageVO> getChatHistory(@PathVariable("roomId") Long roomId){
		return chatService.getChatHistory(roomId);
	}
	
	@GetMapping("/rooms")
	public List<ChatRoomVO> getMyRooms(@RequestParam("userId") String userId){
		return chatRoomService.findRoomsByUserId(userId);
	}
	

}
