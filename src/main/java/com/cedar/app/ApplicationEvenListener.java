package com.cedar.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cedar.app.entity.AuthorityEntity;
import com.cedar.app.entity.RoleEntity;
import com.cedar.app.entity.UserEntity;
import com.cedar.app.repository.AuthorityRepository;
import com.cedar.app.repository.RoleRepository;
import com.cedar.app.repository.UserRepository;
import com.cedar.app.utils.Utils;

@Component
public class ApplicationEvenListener {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	AuthorityRepository authorityRepo;
	
	@Autowired
	Utils utils;

	@EventListener
	@Transactional
	public void onApplicationEven(ApplicationStartedEvent event)
	{
		System.out.println("event listened");
		
		
		RoleEntity entity = createRoles();
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("test@test.com");
	    userEntity.setEncodedPassword(new BCryptPasswordEncoder().encode("password"));
	    userEntity.setUserId(utils.getUserId());
	    userEntity.setFirstName("test");
		userEntity.setRoles(Arrays.asList(entity));
		userEntity.setLastName("test");
		if(userRepo.findUserEntityByEmail("test@test.com")==null)
			userRepo.save(userEntity);
	}

	@Transactional
	private RoleEntity createRoles() {
		List<AuthorityEntity> authorityEntities = createAuthorities();
		RoleEntity entity;
		entity = roleRepo.findByName("ROLE_ADMIN");
		if(entity==null)
		{
			entity = new RoleEntity();
			entity.setAuthorities(authorityEntities);
			entity.setName("ROLE_ADMIN");
			entity = roleRepo.save(entity);
			
		}
		return entity;
	}

	@Transactional
	private List<AuthorityEntity> createAuthorities() {
		List<AuthorityEntity> authorityEntities = new ArrayList<>();
		
		AuthorityEntity readAuthorityEntity = new AuthorityEntity();
		readAuthorityEntity.setName("READ_AUTHORITY");
		if(authorityRepo.findByName(readAuthorityEntity.getName()).size()==0)
			authorityEntities.add(authorityRepo.save(readAuthorityEntity));
		
		AuthorityEntity writeAuthorityEntity = new AuthorityEntity();
		writeAuthorityEntity.setName("WRITE_AUTHORITY");
		if(authorityRepo.findByName(writeAuthorityEntity.getName()).size()==0)
			authorityEntities.add(authorityRepo.save(writeAuthorityEntity));
		
		AuthorityEntity deleteAuthorityEntity = new AuthorityEntity();
		deleteAuthorityEntity.setName("DELETE_AUTHORITY");
		if(authorityRepo.findByName(deleteAuthorityEntity.getName()).size()==0)
			authorityEntities.add(authorityRepo.save(deleteAuthorityEntity));
		
		return authorityEntities;
	}
}
