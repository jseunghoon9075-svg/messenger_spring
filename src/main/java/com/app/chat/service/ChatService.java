package com.app.chat.service;

import java.util.List;

import com.app.chat.domain.vo.ChatMessageVO;

public interface ChatService {

	public void sendMessage(ChatMessageVO chatMessageVO);
	
	public List<ChatMessageVO> getChatHistory(Long chatRoomId);
}
