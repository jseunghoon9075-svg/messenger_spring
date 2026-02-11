package com.app.chat.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.chat.domain.vo.ChatRoomUserVO;
import com.app.chat.mapper.ChatRoomUserMapper;
import com.app.chat.repository.ChatRoomUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRoomUserRepositoryImpl implements ChatRoomUserRepository {
	
	private final ChatRoomUserMapper chatRoomUserMapper;

	@Override
	public void join(ChatRoomUserVO chatRoomUserVO) {
		chatRoomUserMapper.insertChatRoomUser(chatRoomUserVO);
	}

	@Override
	public List<ChatRoomUserVO> findRoomUserByRoomId(Long chatMessageId) {
		return chatRoomUserMapper.selectChatRoomUserByChatRoomId(chatMessageId);
	}

	@Override
	public boolean isJoined(Long chatRoomId, String userId) {
		return chatRoomUserMapper.reconnectUser(chatRoomId, userId) == 1;
	}

	@Override
	public void leave(Long chatRoomId, String userId) {
		chatRoomUserMapper.disconnectUser(chatRoomId, userId);
	}

}
