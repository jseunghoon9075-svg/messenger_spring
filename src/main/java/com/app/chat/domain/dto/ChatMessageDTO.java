package com.app.chat.domain.dto;

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
public class ChatMessageDTO {
	
	private Long messageId;
	private String messageType;
	private String content;
	
	private String userId;
	private String userName;
	
	private LocalDateTime sentAt;
	
	private boolean isMine;
	private boolean isRead;
}
