package com.app.chat.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.chat.domain.vo.ChatMessageReadVO;
import com.app.chat.mapper.ChatMessageMapper;
import com.app.chat.mapper.ChatMessageReadMapper;
import com.app.chat.repository.ChatMessageReadRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageReadRepositoryImpl implements ChatMessageReadRepository {
	
	private final ChatMessageReadMapper chatMessageReadMapper;

	@Override
	public void markAsRead(Long chatMessageId, String userId) {
		ChatMessageReadVO readVO = ChatMessageReadVO.builder()
				.chatMessageId(chatMessageId)
				.userId(userId)
				.build();
		chatMessageReadMapper.insertChatRead(readVO);
	}

	@Override
	public boolean existsRead(Long chatMessageId, String userId) {
		return chatMessageReadMapper.existRead(chatMessageId, userId) == 1;
	}

}
