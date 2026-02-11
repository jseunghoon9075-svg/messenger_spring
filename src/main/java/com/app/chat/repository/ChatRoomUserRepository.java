package com.app.chat.repository;

import java.util.List;

import com.app.chat.domain.vo.ChatRoomUserVO;

public interface ChatRoomUserRepository {
	
	public void join(ChatRoomUserVO chatRoomUserVO);
	
	public List<ChatRoomUserVO> findRoomUserByRoomId(Long chatMessageId);
	
	public boolean isJoined(Long chatRoomId, String userId);
	
	public void leave(Long chatRoomId, String userId);

}
