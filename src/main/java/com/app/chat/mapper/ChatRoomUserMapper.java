package com.app.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.app.chat.domain.vo.ChatRoomUserVO;

@Mapper
public interface ChatRoomUserMapper {
	public int insertChatRoomUser(ChatRoomUserVO chatRoomUserVO);
	
	public List<ChatRoomUserVO> selectChatRoomUserByChatRoomId(Long chatRoomId);
	
	public void reconnectUser(
			@Param("chatRoomId") Long chatRoomId,			
			@Param("userId") String userId
			);
	
	public void disconnectUser(
			@Param("chatRoomId") Long chatRoomId,
			@Param("userId") String userId
			);
}
