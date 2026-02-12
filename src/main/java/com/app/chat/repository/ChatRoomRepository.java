package com.app.chat.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.chat.domain.vo.ChatRoomVO;

import lombok.RequiredArgsConstructor;

public interface ChatRoomRepository {

	public int save(ChatRoomVO chatRoomVO);
	
	public ChatRoomVO findRoomByRoomId(Long chatRoomId);
	
	public List<ChatRoomVO> findRoomByUserId(String userId);
}
