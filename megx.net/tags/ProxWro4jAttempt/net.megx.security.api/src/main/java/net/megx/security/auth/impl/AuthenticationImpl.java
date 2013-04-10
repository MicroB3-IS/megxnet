package net.megx.security.auth.impl;

import java.util.ArrayList;
import java.util.List;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;

public class AuthenticationImpl implements Authentication{
	
	private User user;
	
	public AuthenticationImpl(User user) {
		this.user = user;
	}
	
	@Override
	public Object getUserPrincipal() {
		return user.getLogin();
	}

	@Override
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>(user.getRoles());
		roles.addAll(user.getRoles());
		return roles;
	}

	@Override
	public String toString() {
		return "AuthenticationImpl [user=" + user + "]";
	}
	
	public User getUser(){
		return user.copy();
	}
}
