package com.app.chat.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.chat.domain.vo.UserVO;
import com.app.chat.exception.GlobalException;
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
			throw new GlobalException(HttpStatus.BAD_REQUEST,"INVALID_USER_REQUEST","사용자 요청 값이 올바르지 않습니다.");
		}
		
		userRepository.save(userVO);
	}

	@Override
	public UserVO findUserById(String userId) {
		
		UserVO user = userRepository.findUserById(userId);
		
		if(user == null) {
			throw new GlobalException(HttpStatus.NOT_FOUND,"USER_NOT_FOUND","사용자를 찾을 수 없습니다.");
		}
		return user;
	}

}
