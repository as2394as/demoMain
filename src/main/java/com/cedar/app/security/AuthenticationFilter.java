package com.cedar.app.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cedar.app.SpringApplicationContext;
import com.cedar.app.service.UserService;
import com.cedar.app.service.Impl.UserServiceImpl;
import com.cedar.app.ui.request.model.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try
		{ 
			UserLoginRequestModel model = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
			return authenticationManager
					.authenticate(
							new UsernamePasswordAuthenticationToken
							(model.getEmail(), model.getPassword(),new ArrayList<>()));
		}
		catch(IOException e)
		{
			throw new RuntimeException("runtime exception");
		} 
	}


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((PrincipalUser)authResult.getPrincipal()).getUsername();
		
		String token = Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET).compact();
		
		response.setHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+token);
		
		UserService service =(UserServiceImpl) SpringApplicationContext.getBean("userServiceImpl");
		response.setHeader("UserID", service.getUser(username).getUserId());
	}
	
	

}
