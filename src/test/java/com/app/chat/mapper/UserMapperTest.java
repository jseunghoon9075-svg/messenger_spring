package com.app.chat.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.app.chat.domain.vo.UserVO;

@MybatisTest
@ActiveProfiles("test")
@MapperScan("com.app.chat.mapper")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {
	
	@Autowired
	private UserMapper userMapper;

	@Test
	void insertUserTest() {
		UserVO userVO = UserVO.builder()
				.userId("alslrnf")
				.userName("홍길동")
				.userPassword("1234")
				.build();
		
		userMapper.insertUser(userVO);
		
		UserVO saved = userMapper.selectUserById("alslrnf");
		
		assertNotNull(saved);
		assertEquals("홍길동", saved.getUserName());
	}

}
