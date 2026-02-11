package com.app.chat.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.chat.domain.vo.ChatRoomUserVO;
import com.app.chat.domain.vo.ChatRoomVO;
import com.app.chat.exception.GlobalException;
import com.app.chat.repository.ChatRoomRepository;
import com.app.chat.repository.ChatRoomUserRepository;
import com.app.chat.repository.UserRepository;
import com.app.chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomServiceImpl implements ChatRoomService {
	
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomUserRepository chatRoomUserRepository;
	private final UserRepository userRepository;

//	채팅방 생성
	@Override
	@Transactional
	public Long createRoom(ChatRoomVO chatRoomVO, List<String> userIds) {
		
		if(chatRoomVO == null) {
			throw new GlobalException("채팅방 정보가 없습니다.");
		}
		
		if(userIds == null) {
			throw new GlobalException("참여자 정보가 없습니다.");
		}
//		채팅방 저장
		chatRoomRepository.save(chatRoomVO);
		
		Long roomId = chatRoomVO.getChatRoomId();
//		참여자 저장
		for(String userId : userIds) {
			
			if(userRepository.findUserById(userId) == null) {
				throw new GlobalException("존재하지 않는 사용자입니다.");
			}
			
			ChatRoomUserVO roomUser = new ChatRoomUserVO();
			
			roomUser.setChatRoomId(roomId);
			roomUser.setUserId(userId);
			
			chatRoomUserRepository.join(roomUser);
		}
		
		return roomId;
	}

//	채팅방 조회
	@Override
	public ChatRoomVO findRoomById(Long chatRoomId) {
		
		ChatRoomVO room = chatRoomRepository.findRoomByRoomId(chatRoomId);
		
		if(room == null) {
			throw new GlobalException("존재하지 않는 채팅방입니다.");
		}
		return room;
	}

//	유저가 채팅방에 속해있는지 체크
	@Override
	public boolean isUserInRoom(Long chatRoomId, String userId) {
		return chatRoomUserRepository.isJoined(chatRoomId, userId);
	}

//	방 참여자 목록 조회
	@Override
	public List<ChatRoomUserVO> findRoomUsers(Long chatRoomId) {
		return chatRoomUserRepository.findRoomUserByRoomId(chatRoomId);
	}

}
