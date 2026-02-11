package com.app.chat.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.app.chat.domain.vo.ChatRoomVO;

@Mapper
public interface ChatRoomMapper {
	
	public int insertChatRoom(ChatRoomVO chatRoomVO);
	
	public ChatRoomVO selectChatRoomById(Long chatRoomId);
	
	public ChatRoomVO selectChatRoomsByUserId(String userId);
}
