package com.cedar.app.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class RoleEntity implements Serializable {

	private static final long serialVersionUID = 2980115106319028904L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false,length = 20)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private List<UserEntity> users;
	
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(name = "roles_authorities",
	joinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "authorities_id",referencedColumnName = "id"))
	private List<AuthorityEntity> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public List<AuthorityEntity> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityEntity> authorities) {
		this.authorities = authorities;
	}

}
