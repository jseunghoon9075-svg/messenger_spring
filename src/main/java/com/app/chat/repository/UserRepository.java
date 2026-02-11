package com.app.chat.repository;

import java.util.List;

import com.app.chat.domain.vo.UserVO;

public interface UserRepository {

	public void save(UserVO userVO);
	
	public UserVO findUserById(String userId);
	
	public boolean existsByUserId(String userId);
}
