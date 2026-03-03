package com.app.chat.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.chat.domain.vo.ChatMessageVO;

public interface ChatMessageRepository {

	public void save(ChatMessageVO chatMessageVO);
	
	public List<ChatMessageVO> findMessageByRoomId(Long chatRoomId);
	
	public List<ChatMessageVO> findRecentMessages(Long chatRoomId, int limit);
	
	public List<ChatMessageVO> findOldMessages(Long chatRoomId, int limit);
	
	public Long findCountByRoomId(Long chatRoomId);
	
	public void archiveMessages(@Param("ids") List<Long> ids);
}
