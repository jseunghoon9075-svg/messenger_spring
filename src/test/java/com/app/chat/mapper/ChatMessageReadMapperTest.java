package com.app.chat.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.module.ModuleDescriptor.Builder;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.app.chat.domain.vo.ChatMessageReadVO;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ChatMessageReadMapperTest {
	
	@Autowired
	private ChatMessageReadMapper chatMessageReadMapper;
	
	@Test
	public void insertAndExistsReadTest() {
		ChatMessageReadVO readVO = ChatMessageReadVO.builder()
				.chatMessageId(1L)
				.userId("alslrnf")
				.build();
		
		chatMessageReadMapper.insertChatRead(readVO);
		
		int exists = chatMessageReadMapper.existRead(1L, "alslrnf");
		assertEquals(1, exists);
		System.out.print("test성공 : " + exists);
	}
	
}
