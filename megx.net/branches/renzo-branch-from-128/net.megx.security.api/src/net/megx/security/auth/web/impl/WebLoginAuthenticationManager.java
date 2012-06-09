package net.megx.security.auth.web.impl;

import net.megx.security.auth.AccessDeniedException;
import net.megx.security.auth.Authentication;
import net.megx.security.auth.InsufficientAuthenticationException;
import net.megx.security.auth.Role;
import net.megx.security.auth.WebResource;
import net.megx.security.auth.impl.BaseAuthenticationManager;

public class WebLoginAuthenticationManager extends BaseAuthenticationManager{

	@Override
	protected Class<?> getProtectedObjectBaseClass() {
		return WebResource.class;
	}

	@Override
	protected void doCheckAuthentication(Authentication authentication,
			Object object) throws AccessDeniedException,
			InsufficientAuthenticationException {
		if(authentication == null){
			throw new InsufficientAuthenticationException("Not authenticated!");
		}
		WebResource resource = (WebResource)object;
		boolean authOk = false;
		for(Role role: authentication.getRoles()){
			if(resource.getRoles().contains(role)){
				authOk = true;
				break;
			}
		}
		if(!authOk)
			throw new AccessDeniedException("The requesting auth does not have the required roles to access this resource!");
	}

}
