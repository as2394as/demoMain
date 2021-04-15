package com.cedar.app.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cedar.app.entity.AuthorityEntity;
import com.cedar.app.entity.RoleEntity;
import com.cedar.app.entity.UserEntity;

public class PrincipalUser implements UserDetails {

	private static final long serialVersionUID = -316187646778730223L;
	private UserEntity user;
	
	public PrincipalUser(UserEntity user) {
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<AuthorityEntity> authorityEntities = new ArrayList<>();
		for(RoleEntity role:user.getRoles())
		{
			GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
			authorities.add(authority);
			authorityEntities.addAll(role.getAuthorities());
		}
		authorityEntities.forEach(a->authorities.add(new SimpleGrantedAuthority(a.getName())));
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getEncodedPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
