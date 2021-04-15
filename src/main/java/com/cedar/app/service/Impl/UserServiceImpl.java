package com.cedar.app.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cedar.app.dto.UserDto;
import com.cedar.app.entity.UserEntity;
import com.cedar.app.repository.UserRepository;
import com.cedar.app.security.PrincipalUser;
import com.cedar.app.service.UserService;
import com.cedar.app.utils.UserNamePasswordWrongException;
import com.cedar.app.utils.Utils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private Utils utils;

	@Override
	public UserDto createUser(UserDto dto) {
		UserEntity entity = new UserEntity();
		UserDto savedDto = new UserDto();
		dto.setUserId(utils.getUserId());
		dto.setEncodedPassword(passwordEncoder.encode(dto.getPassword()));
		BeanUtils.copyProperties(dto, entity);
		
		if(repo.findUserEntityByEmail(dto.getEmail())!=null)
			throw new RuntimeException("user already there");
		UserEntity savedEntity = repo.save(entity);
		BeanUtils.copyProperties(savedEntity, savedDto);
		return savedDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto dto = new UserDto();
		UserEntity entity = repo.findUserEntityByEmail(username);
		if(entity!=null)
		{
		BeanUtils.copyProperties(entity, dto);
		}	
		else
		{
			throw new UsernameNotFoundException("uer not found");
		}
		BeanUtils.copyProperties(entity, dto);
		return new PrincipalUser(entity);
	}

	@Override
	public UserDto getUser(String username) {
		UserDto dto = new UserDto();
		UserEntity entity = repo.findUserEntityByEmail(username);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public UserDto getUserByUserId(String id) {
		UserDto dto = new UserDto();
		UserEntity entity = repo.findUserByUserId(id);
		if(entity!=null)
		{
		BeanUtils.copyProperties(entity, dto);
		}
		else
		{
			throw new UserNamePasswordWrongException("the credentials are wrong");
		}
		return dto;
	}

	@Override
	public UserDto updateUser(UserDto input) {
		UserDto dto = new UserDto();
		UserEntity entity = repo.findUserEntityByEmail(input.getEmail());
 		if(entity!=null)
		{
			entity.setFirstName(input.getFirstName());
			entity.setLastName(input.getLastName());
			repo.save(entity);
			BeanUtils.copyProperties(entity, dto);
		}
		else
		{
			throw new UserNamePasswordWrongException("the credentials are wrong");
		}
		return dto;
	}

	@Override
	public List<UserDto> getAllUsers(int page,int limit) {
		if(page>0)
		{
			page=page-1;
		}
		List<UserDto> list = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, limit);
		for(UserEntity entity: repo.findAll(pageable))
		{
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(entity, dto);
			list.add(dto);
		}
		return list;
	}

	@Override
	public UserDto getUserFromUserId(String id) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(repo.getUserByUserId(id),userDto);
		return userDto;
	}

	@Override
	public boolean deleteUser(String userId){
		UserDto userDto = new UserDto();
		UserEntity userEntity = new UserEntity();
		userEntity = repo.getUserByUserId(userId);
		if(userEntity!=null)
		{
			repo.delete(userEntity);
			return true;
		}
		else
		{
			throw new UserNamePasswordWrongException(userId);
		}
		
	}

	@Override
	public List<UserDto> getAllUsers() {
		
			Iterable<UserEntity> list = repo.findAll();
			List<UserDto> userDtos = new ArrayList<>();
			for(UserEntity userEntity:list)
			{
				UserDto userDto = new UserDto();
				BeanUtils.copyProperties(userEntity, userDto);
				userDtos.add(userDto);
			}
		return userDtos ;
	}

}
