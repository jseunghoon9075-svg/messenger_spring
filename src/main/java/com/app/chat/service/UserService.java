package com.app.chat.service;

import com.app.chat.domain.vo.UserVO;

public interface UserService {
	
	public void register(UserVO userVO);
	
	public UserVO findUserById(String userId);
}
