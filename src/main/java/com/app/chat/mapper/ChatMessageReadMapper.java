package com.app.chat.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.app.chat.domain.vo.ChatMessageReadVO;

@Mapper
public interface ChatMessageReadMapper {

	public void insertChatRead(ChatMessageReadVO chatMessageReadVO);
	
	public int existRead(
			@Param("chatMessageId") Long chatMessageId, 
			@Param("userId") String userId
			);
	
	public int countUnreadMessages(
			@Param("chatRoomId") Long chatRoomId, 
			@Param("userId") String userId
			);
	
	public int countReadUsers(Long chatMessageId);
}
