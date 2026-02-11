package com.app.chat.domain.vo;

import java.awt.TrayIcon.MessageType;
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
public class ChatRoomUserVO {
	
	private Long chatRoomUserId;
	private String userId;
	private Long chatRoomId;
	
	private LocalDateTime joinedAt;
	private LocalDateTime leftAt;
	
	private String isConnected;
}
