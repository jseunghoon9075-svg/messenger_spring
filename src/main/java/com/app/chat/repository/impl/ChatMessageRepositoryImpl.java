package com.app.chat.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.chat.domain.vo.ChatMessageVO;
import com.app.chat.mapper.ChatMessageMapper;
import com.app.chat.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {
	
	private final ChatMessageMapper chatMessageMapper;

	@Override
	public void save(ChatMessageVO chatMessageVO) {
		chatMessageMapper.insertChatMessage(chatMessageVO);
	}

	@Override
	public List<ChatMessageVO> findMessageByRoomId(Long chatRoomId) {
		return chatMessageMapper.selectMessagesByChatRoomId(chatRoomId);
	}

	@Override
	public List<ChatMessageVO> findRecentMessages(Long chatRoomId, int limit) {
		return chatMessageMapper.selectRecentMessages(chatRoomId, limit);
	}

	@Override
	public Long findCountByRoomId(Long chatRoomId) {
		return chatMessageMapper.selectCountByRoomId(chatRoomId);
	}

	@Override
	public List<ChatMessageVO> findOldMessages(Long chatRoomId, int limit) {
		return chatMessageMapper.selectOldMessages(chatRoomId, limit);
	}

	@Override
	public void archiveMessages(List<Long> ids) {
		chatMessageMapper.archiveMessages(ids);
	}
	
	

}
