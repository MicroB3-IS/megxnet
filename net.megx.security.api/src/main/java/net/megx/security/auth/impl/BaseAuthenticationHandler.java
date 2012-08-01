package net.megx.security.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.security.auth.AuthenticationHandler;
import net.megx.security.auth.SecurityContext;

public class BaseAuthenticationHandler implements AuthenticationHandler{
	
	private SecurityContext securityContext;
	
	protected Log log = LogFactory.getLog(getClass());
	
	public BaseAuthenticationHandler(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}
	
	@Override
	public SecurityContext getSecurityContext() {
		return securityContext;
	}

}
