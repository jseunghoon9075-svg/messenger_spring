package com.app.chat.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.chat.domain.vo.ChatRoomVO;
import com.app.chat.mapper.ChatRoomMapper;
import com.app.chat.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {
	
	private final ChatRoomMapper chatRoomMapper;

	@Override
	public int save(ChatRoomVO chatRoomVO) {
		return chatRoomMapper.insertChatRoom(chatRoomVO);
	}

	@Override
	public ChatRoomVO findRoomByRoomId(Long chatRoomId) {
		return chatRoomMapper.selectChatRoomByRoomId(chatRoomId);
	}

	@Override
	public ChatRoomVO findRoomByUserId(String userId) {
		return chatRoomMapper.selectChatRoomsByUserId(userId);
	}

}
