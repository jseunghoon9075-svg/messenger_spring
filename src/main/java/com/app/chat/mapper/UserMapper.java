package com.app.chat.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.app.chat.domain.vo.UserVO;

@Mapper
public interface UserMapper {
	public void insertUser(UserVO userVO);
	
	public UserVO selectUserById(String userId);
}
