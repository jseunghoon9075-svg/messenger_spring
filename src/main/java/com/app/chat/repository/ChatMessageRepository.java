package com.app.chat.repository;

import java.util.List;

import com.app.chat.domain.vo.ChatMessageVO;

public interface ChatMessageRepository {

	public void save(ChatMessageVO chatMessageVO);
	
	public List<ChatMessageVO> findMessageByRoomId(Long chatRoomId);
}
