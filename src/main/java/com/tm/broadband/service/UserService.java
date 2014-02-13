package com.tm.broadband.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.UserMapper;
import com.tm.broadband.model.User;

@Service
public class UserService {
	
	private UserMapper userMapper;

	@Autowired
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public UserService() {}
	
	@Transactional
	public User queryUserLogin(User user) {
		return this.userMapper.selectUserLogin(user);
	}
	

}
