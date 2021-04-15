package com.cedar.app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.cedar.app.SpringApplicationContext;
import com.cedar.app.entity.UserEntity;
import com.cedar.app.repository.UserRepository;
import com.cedar.app.service.UserService;
import com.cedar.app.service.Impl.UserServiceImpl;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	UserRepository repository;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager,UserRepository repo) {
		super(authenticationManager);
		this.repository=repo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		if(header!=null && header.startsWith(SecurityConstants.TOKEN_PREFIX))
		{
			UsernamePasswordAuthenticationToken authenticationToken = gerAuthentication(request);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			chain.doFilter(request, response);
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

	private UsernamePasswordAuthenticationToken gerAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		UserService service =(UserServiceImpl) SpringApplicationContext.getBean("userServiceImpl");
		if(token!=null)
		{
			token =token.replace(SecurityConstants.TOKEN_PREFIX, "");
		}
		
		String user = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET)
				.parseClaimsJws(token).getBody().getSubject();
		UserEntity entity = repository.findUserEntityByEmail(user);
		return new UsernamePasswordAuthenticationToken(new PrincipalUser(entity), null,new PrincipalUser(entity).getAuthorities());
	}

	
}
