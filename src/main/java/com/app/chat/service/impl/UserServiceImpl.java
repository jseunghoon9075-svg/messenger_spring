package com.app.chat.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.chat.domain.vo.UserVO;
import com.app.chat.repository.UserRepository;
import com.app.chat.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 조회는 기본적으로 readOnly -> 성능 최적화 
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public void register(UserVO userVO) {
		
		if(userVO == null) {
			throw new IllegalArgumentException("User 정보가 없습니다.");
		}
		
		userRepository.save(userVO);
	}

	@Override
	public UserVO findUserById(String userId) {
		
		UserVO user = userRepository.findUserById(userId);
		
		if(user == null) {
			throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
		}
		return user;
	}

}
