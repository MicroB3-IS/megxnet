package net.megx.security.auth;

import java.util.List;

import net.megx.security.auth.model.Role;

public interface Authentication {
	public Object getUserPrincipal();
	public List<Role> getRoles();
}
