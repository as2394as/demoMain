package com.cedar.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cedar.app.dto.UserDto;

public interface UserService extends UserDetailsService {
	
	UserDto createUser(UserDto dto);
	
	UserDto getUser(String username);

	UserDto getUserByUserId(String id);
	
	UserDto updateUser(UserDto dto);

	List<UserDto> getAllUsers(int page, int limit);
	
	UserDto getUserFromUserId(String id);
	
	boolean deleteUser(String userId);
	
	List<UserDto> getAllUsers();

}
