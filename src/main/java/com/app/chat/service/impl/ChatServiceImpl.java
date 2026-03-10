package com.app.chat.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.chat.domain.vo.ChatMessageVO;
import com.app.chat.exception.GlobalException;
import com.app.chat.repository.ChatMessageRepository;
import com.app.chat.repository.UserRepository;
import com.app.chat.service.ChatRoomService;
import com.app.chat.service.ChatService;
import com.app.chat.type.MessageType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {
	
	private final ChatMessageRepository chatMessageRepository;
	private final UserRepository userRepository;
	private final ChatRoomService chatRoomService;
	private static final String AI_USER_ID = "AI";

	@Override
	@Transactional
	public void sendMessage(ChatMessageVO chatMessageVO) {
		
		if(chatMessageVO == null) {
			throw new GlobalException(HttpStatus.BAD_REQUEST,"INVALID_MESSAGE","메세지 정보가 없습니다.");
		}
		
		if(chatMessageVO.getUserId() == null) {
			throw new GlobalException(HttpStatus.BAD_REQUEST,"INVALID_USER_ID","사용자 정보가 없습니다.");
		}
		
		if(chatMessageVO.getChatRoomId() == null) {
			throw new GlobalException(HttpStatus.BAD_REQUEST,"INVALID_ROOD_ID","채팅방 정보가 없습니다.");
		}
		
		if(chatMessageVO.getContent() == null || chatMessageVO.getContent().trim().isEmpty()) {
			throw new GlobalException(HttpStatus.BAD_REQUEST,"EMPTY_MESSAGE_CONTENT","메세지 내용이 비어 있습니다.");
		}
		
		chatRoomService.findRoomById(chatMessageVO.getChatRoomId());
		
		if(!AI_USER_ID.equals(chatMessageVO.getUserId())) {
			boolean isUserInRoom = 
					chatRoomService.isUserInRoom(
							chatMessageVO.getChatRoomId(), 
							chatMessageVO.getUserId());
			if(!isUserInRoom) {
				throw new GlobalException(HttpStatus.FORBIDDEN,"NOT_ROOM_USER","해당 채팅방에 속해 있지 않은 사용자입니다.");
			}
		};
		
		
		
		if(chatMessageVO.getMessageType() == null) {
			chatMessageVO.setMessageType(MessageType.MESSAGE);
		}
		
		chatMessageRepository.save(chatMessageVO);
	}

	@Override
	public List<ChatMessageVO> getChatHistory(Long chatRoomId) {
		
		if(chatRoomId == null) {
			throw new GlobalException(HttpStatus.NOT_FOUND,"ROOM_NOT_FOUND","채팅방 ID가 없습니다.");
		}
		return chatMessageRepository.findMessageByRoomId(chatRoomId);
	}

	

}
