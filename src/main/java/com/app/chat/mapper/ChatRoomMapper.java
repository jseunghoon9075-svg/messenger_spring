package com.app.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.app.chat.domain.vo.ChatRoomVO;

@Mapper
public interface ChatRoomMapper {
	
	public int insertChatRoom(ChatRoomVO chatRoomVO);
	
	public ChatRoomVO selectChatRoomByRoomId(Long chatRoomId);
	
	public List<ChatRoomVO> selectChatRoomsByUserId(String userId);
	
	public void updateSummary(
			@Param("summary") String summary, 
			@Param("chatRoomId") Long chatRoomId);
}
