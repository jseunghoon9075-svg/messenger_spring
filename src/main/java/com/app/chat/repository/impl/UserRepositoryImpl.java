package com.app.chat.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.chat.domain.vo.UserVO;
import com.app.chat.mapper.UserMapper;
import com.app.chat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
	
	private final UserMapper userMapper;

	@Override
	public void save(UserVO userVO) {
		userMapper.insertUser(userVO);
	}

	@Override
	public UserVO findUserById(String userId) {
		return userMapper.selectUserById(userId);
	}

//	중복확인 존재하면 true
	@Override
	public boolean existsByUserId(String userId) {
		return userMapper.selectUserById(userId) != null;
	}

}
