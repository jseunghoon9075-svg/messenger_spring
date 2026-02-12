package com.app.chat.domain.vo;

import java.time.LocalDateTime;

import com.app.chat.type.MessageType;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "메세지 정보")
public class ChatMessageVO {
	private Long chatMessageId;
	
	@Schema(description = "메세지 타입", example = "MESSAGE")
	private MessageType messageType;
	
	@Schema(description = "메세지 내용", example = "안녕하세요")
	private String content;
	
	private LocalDateTime sentAt;
	
	@Schema(description = "채팅방 ID", example = "1")
	private Long chatRoomId;
	
	@Schema(description = "사용자 ID", example = "멋쟁이쿠키")
	private String userId;
}
