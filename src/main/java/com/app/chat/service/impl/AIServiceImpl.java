package com.app.chat.service.impl;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.chat.domain.vo.ChatMessageVO;
import com.app.chat.domain.vo.ChatRoomVO;
import com.app.chat.external.OpenAIClient;
import com.app.chat.repository.ChatMessageRepository;
import com.app.chat.repository.ChatRoomRepository;
import com.app.chat.service.AIService;
import com.app.chat.type.MessageType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {
	
	private final ChatMessageRepository chatMessageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final SimpMessagingTemplate messagingTemplate;
	private final OpenAIClient openAIClient;

	@Override
	@Async		// @비동기 분리해서 WebSocket 처리 지연 방지
	@Transactional
	public void generateReply(Long roomId, String userMessage) {
		
		
		ChatRoomVO room = chatRoomRepository.findRoomByRoomId(roomId);
		
		String summary = room.getSummary();
		
		List<ChatMessageVO> history = chatMessageRepository.findRecentMessages(roomId, 5);
		
		
		String aiReply = openAIClient.askWithContext(summary, history);
		
		int totalTokens = openAIClient.getLastTotalTokens();
		
		if(totalTokens > 800) {
			
			List<ChatMessageVO> oldMessages = chatMessageRepository.findOldMessages(roomId, 10);
			
			StringBuilder combined = new StringBuilder();
			
			
			if(summary != null && !summary.isBlank()) {
				combined.append("이전 요약:\n")
						.append(summary)
						.append("\n\n");
			}
			
			for(ChatMessageVO message:oldMessages) {
				combined.append(message.getUserId())
						.append(": ")
						.append(message.getContent())
						.append("\n");
			}
			
			String newSummary = openAIClient.summarizeText(combined.toString());
			
			chatRoomRepository.modifySummary(newSummary, roomId);
			
			List<Long> ids = oldMessages.stream()
					.map(ChatMessageVO::getMessageId)
					.toList();
			System.out.println("IDS: " + ids);
			
			chatMessageRepository.archiveMessages(ids);
			
			summary = newSummary;
			history = chatMessageRepository.findRecentMessages(roomId, 5);
			
			aiReply = openAIClient.askWithContext(summary, history);
		}
		
		
		
		ChatMessageVO aiMessage = new ChatMessageVO();
		aiMessage.setChatRoomId(roomId);
		aiMessage.setUserId("AI");
		aiMessage.setContent(aiReply);
		aiMessage.setMessageType(MessageType.SYSTEM);
		
		chatMessageRepository.save(aiMessage);
		
		
		messagingTemplate.convertAndSend(
				"/topic/room/" + roomId,
				aiMessage
				);
	}
	
}
