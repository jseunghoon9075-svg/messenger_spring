package com.app.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.app.chat.domain.vo.ChatMessageVO;

@Mapper
public interface ChatMessageMapper {
	
	public int insertChatMessage(ChatMessageVO chatMessageVO);
	
	public List<ChatMessageVO> selectMessagesByChatRoomId(Long chatRoomId);
	
	public ChatMessageVO selectLastMessageByChatRoomId(Long chatRoomId);
	
	public List<ChatMessageVO> selectRecentMessages(
			@Param("chatRoomId") Long chatRoomId,
			@Param("limit") int limit
			);
	
	public List<ChatMessageVO> selectOldMessages(
			@Param("chatRoomId") Long chatRoomId,
			@Param("limit") int limit
			);
	
	public Long selectCountByRoomId(Long chatRoomId);
	
	public void archiveMessages(@Param("ids") List<Long> ids);
}
