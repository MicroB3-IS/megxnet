package net.megx.security.auth.web.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.security.auth.AccessDeniedException;
import net.megx.security.auth.Authentication;
import net.megx.security.auth.InsufficientAuthenticationException;
import net.megx.security.auth.impl.BaseAuthenticationManager;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.WebResource;

public class WebLoginAuthenticationManager extends BaseAuthenticationManager{
	
	private Log log = LogFactory.getLog(getClass());
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
		log.debug("Successful authentication: " + authentication);
	}

}
