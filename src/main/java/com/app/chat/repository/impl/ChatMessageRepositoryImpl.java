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

}
