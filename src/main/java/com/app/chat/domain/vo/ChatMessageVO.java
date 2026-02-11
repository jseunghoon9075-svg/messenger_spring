package com.app.chat.domain.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageVO {
	private Long chatMessageId;
	
	private String messageType;
	
	private String content;
	
	private LocalDateTime sentAt;
	
	private Long chatRoomId;
	private String userId;
}
