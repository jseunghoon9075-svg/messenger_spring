package com.app.chat.repository;

import java.util.List;

import com.app.chat.domain.vo.ChatRoomUserVO;

public interface ChatRoomUserRepository {
	
	public int save(ChatRoomUserVO chatRoomUserVO);
	
	public List<ChatRoomUserVO> findRoomUserByRoomId(Long chatMessageId);

}
