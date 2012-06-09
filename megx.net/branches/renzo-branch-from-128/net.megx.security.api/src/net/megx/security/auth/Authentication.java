package net.megx.security.auth;

import java.util.List;

public interface Authentication {
	public Object getUserPrincipal();
	public List<Role> getRoles();
}
