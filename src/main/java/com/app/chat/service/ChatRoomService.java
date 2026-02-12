package com.app.chat.service;

import java.util.List;

import com.app.chat.domain.vo.ChatRoomUserVO;
import com.app.chat.domain.vo.ChatRoomVO;

public interface ChatRoomService {
	
	public Long createRoom(ChatRoomVO chatRoomVO, List<String> userIds);
	
	public ChatRoomVO findRoomById(Long chatRoomId);
	
	public boolean isUserInRoom(Long chatRoomId, String userId);
	
	public List<ChatRoomUserVO> findRoomUsers(Long chatRoomId);
	
	public List<ChatRoomVO> findRoomsByUserId(String userId);
}
