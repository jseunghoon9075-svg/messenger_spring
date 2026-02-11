package com.app.chat.mapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import com.app.chat.domain.vo.ChatMessageVO;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ChatMessageMapperTest {

	@Autowired
	ChatMessageMapper chatMessageMapper;
	
	@Test
	void insertAndSelectMessageTest() {
		ChatMessageVO msg = ChatMessageVO.builder()
				.chatRoomId(1L)
				.userId("alslrnf")
				.messageType("CHAT")
				.content("안녕!")
				.build();
		
		chatMessageMapper.insertChatMessage(msg);
		
		List<ChatMessageVO> listMessage = chatMessageMapper.selectMessagesByChatRoomId(1L);
		assertFalse(listMessage.isEmpty());
		System.out.print("테스트 성공 :" + listMessage.size());
	}
	
}
