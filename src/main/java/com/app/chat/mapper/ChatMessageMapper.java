package com.app.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.app.chat.domain.vo.ChatMessageVO;

@Mapper
public interface ChatMessageMapper {
	
	public int insertChatMessage(ChatMessageVO chatMessageVO);
	
	public List<ChatMessageVO> selectMessagesByChatRoomId(Long chatRoomId);
	
	public ChatMessageVO selectLastMessageByChatRoomId(Long chatRoomId);
}
