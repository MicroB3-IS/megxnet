package net.megx.security.auth.impl;

import net.megx.security.auth.AuthenticationHandler;
import net.megx.security.auth.SecurityContext;

public class BaseAuthenticationHandler implements AuthenticationHandler{
	
	private SecurityContext securityContext;
	
	public BaseAuthenticationHandler(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}
	
	@Override
	public SecurityContext getSecurityContext() {
		return securityContext;
	}

}
