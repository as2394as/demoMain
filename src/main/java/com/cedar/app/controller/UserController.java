package com.cedar.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cedar.app.dto.UserDto;
import com.cedar.app.service.UserService;
import com.cedar.app.ui.request.model.UserRequestModel;
import com.cedar.app.ui.response.model.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers(@RequestParam("page") int page,@RequestParam("limit") int limit) {
		List<UserRest> list = new ArrayList<>();
		List<UserDto> userDtos = userService.getAllUsers(page,limit);
		for(UserDto dto : userDtos)
		{
			UserRest rest = new UserRest();
			BeanUtils.copyProperties(dto, rest);
			list.add(rest);
		}
		

		return list;

	}
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/{id}")
	public UserRest getUser(@PathVariable String id) {
		UserRest rest = new UserRest();
		UserDto dto = userService.getUserFromUserId(id);
		BeanUtils.copyProperties(dto, rest);

		return rest;

	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
	, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserRequestModel requestModel) {
		UserRest rest = new UserRest();
		BeanUtils.copyProperties(requestModel, rest);
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(requestModel, dto);
		UserDto savedDto = userService.createUser(dto);
		rest.setUserId(savedDto.getUserId());
		return rest;
	}

	@DeleteMapping(path = "/{userId}")
	public String deleteUser(@PathVariable String userId)
	{
			
		  userService.deleteUser(userId);
		 
		return "deleted";
	}

	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
	, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@RequestBody UserRest input) {
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(input, dto);
		UserDto savedDto = userService.updateUser(dto);
		UserRest output = new UserRest();
		BeanUtils.copyProperties(savedDto, output);
		return output;
	}
}
